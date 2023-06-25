package com.anonos

import com.anonos.dto.ResponseDTO
import com.anonos.dto.TransactionDTO
import com.anonos.mapper.toResponseDTO
import com.anonos.service.TransactionStatisticsService
import com.anonos.service.TransactionStatisticsServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.InputStream
import java.nio.file.Path
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.io.path.exists

enum class Format {
    JSON {
        override fun format(responseDTO: ResponseDTO): String {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO)
        }
    },
    TEXT {
        /**
        ==== Run Summary ====
        Input filename: src/main/test/resources/transaction_data.csv
        Total records processed: X
        Total transaction amount: N
        Transaction amount per day:
        yyyy-mm-dd: N1
        yyyy-mm-dd: N2
        ...
        yyyy-mm-dd: NX

        ==== End Run Summary ====
         */
        override fun format(responseDTO: ResponseDTO): String {
            val result = StringBuffer()
            result.append("==== Run Summary ====\n")
            result.append("Input filename: ${responseDTO.inputFilename}\n")
            result.append("Total records processed: ${responseDTO.totalRecords}\n")
            result.append("Total transaction amount: ${responseDTO.totalTransactionAmount}\n")
            responseDTO.transactionAmountsPerDay.forEach {
                result.append("${it.key}: ${it.value}\n")
            }
            result.append("==== End Run Summary ====")
            return result.toString()
        }
    };

    abstract fun format(responseDTO: ResponseDTO): String


}

val dateFormatter = DateTimeFormatter.ofPattern("E MMM d yyyy HH:mm:ss 'GMT'X", Locale.US)
val objectMapper = ObjectMapper()

// Fri Aug 17 2018 00:47:27 GMT-0600 (Mountain Daylight Time)
fun parseDate(input: String): ZonedDateTime {
    return ZonedDateTime.parse(input.subSequence(0, 33), dateFormatter)
}

fun readFrom(file: String): InputStream {
    if (!Path.of(file).exists()) {
        throw IllegalArgumentException("File $file doesn't exists")
    }

    if (!file.endsWith(".csv")) {
        throw IllegalArgumentException("Not a CSV file")
    }

    return File(file).inputStream()
}

fun readStrictCsv(inputStream: InputStream): List<TransactionDTO> = csvReader().open(inputStream) {
    readAllWithHeaderAsSequence().map {
        TransactionDTO(
            address = it["address"]!!,
            age = it["age"]!!.toInt(),
            email = it["email"]!!,
            firstName = it["firstName"]!!,
            ip = it["ip"]!!,
            joinDate = parseDate(it["joinDate"]!!),
            lastName = it["lastName"]!!,
            leaveDate = parseDate(it["leaveDate"]!!),
            referral = it["referral"].toBoolean(),
            transactionAmount = it["transactionAmount"]!!.toBigDecimal(),
            transactionDate = parseDate(it["transactionDate"]!!),
            zip = it["zip"]!!,
        )
    }.toList()

    // TODO validation,
}


fun main(args: Array<String>) {
    val parser = ArgParser("data-analyzer")
    val input by parser.option(ArgType.String, shortName = "i", description = "Input file").required()

    val outputFormatter by parser.option(ArgType.Choice<Format>(), shortName = "f", description = "Output format")
        .default(Format.JSON)
//    val stringFormat by parser.option(
//        ArgType.Choice(listOf("text", "json"), { it }),
//        shortName = "sf",
//        description = "Format as string for output file",
//    ).default("csv")

    val debug by parser.option(ArgType.Boolean, shortName = "d", description = "Turn on debug mode").default(false)

    parser.parse(args)
    val inputData = readStrictCsv(readFrom(input))

    runBlocking {
        val result = TransactionStatisticsServiceImpl().calculate(inputData)
        println(outputFormatter.format(result.toResponseDTO(input)))
    }
}
