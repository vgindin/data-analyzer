package com.anonos.service

import com.anonos.dto.BatchStatDTO
import com.anonos.dto.ProcessingResultDTO
import com.anonos.dto.TransactionDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigDecimal

class TransactionStatisticsServiceImpl: TransactionStatisticsService {
    override suspend fun calculate(inputData: List<TransactionDTO>): ProcessingResultDTO {
        // map
        val buffered: Map<String, List<TransactionDTO>> = inputData.groupBy {
            it.transactionDate.toLocalDate().toString()
        }

        // calc
        val result = coroutineScope {
            val jobs = buffered.entries.map {
                async { stat(it.key, it.value) }
            }
            jobs.awaitAll().associateBy { it.key }
        }

        // reduce
        return result.entries.fold(ProcessingResultDTO(0, BigDecimal.ZERO, result)) { p, e ->
            ProcessingResultDTO(
                p.totalRecords + e.value.totalRecords,
                p.totalTransactionAmount.add(e.value.totalTransactionAmount),
                result,
            )
        }
    }

    private fun stat(key: String, inputData: List<TransactionDTO>): BatchStatDTO {
        return inputData.fold(BatchStatDTO(key, 0, BigDecimal.ZERO)) { b, trx ->
            BatchStatDTO(b.key, b.totalRecords + 1, b.totalTransactionAmount.add(trx.transactionAmount))
        }
    }

}