package com.anonos.service

import com.anonos.dto.ProcessingResultDTO
import com.anonos.dto.TransactionDTO

interface TransactionStatisticsService {
    suspend fun calculate(inputData: List<TransactionDTO>): ProcessingResultDTO
}
