package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.models.db.BroRepository
import org.rmgsoc.bratets.models.db.BroResponseRepository
import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.rmgsoc.bratets.services.telegram.TelegramTextMessageHandler
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.annotation.PostConstruct

@Service
class BratetsModuleService(
        val telegramConnectorService: TelegramConnectorService,
        val broRepository: BroRepository,
        val broResponseRepository: BroResponseRepository
) : TelegramTextMessageHandler {

    private var lastBrattsyTime = Instant.EPOCH

    @PostConstruct
    fun setup() {
        telegramConnectorService.addTextMessageHandler(this)
    }

    private val BratsyLiteral = "братцы"

    override fun isRelevant(text: String): Boolean {
        if (text.length < BratsyLiteral.length) return false

        return normalize(text) == BratsyLiteral
    }

    override fun processMessage(text: String, from: Long) {
        if (lastBrattsyTime.plus(2, ChronoUnit.MINUTES).isBefore(Instant.now())) {
            telegramConnectorService.sendTextMessageToChat(from, "Братцы")

            lastBrattsyTime = Instant.now()
        }
    }

    private fun normalize(text: String) : String {
        return text.substring(0..5).toLowerCase()
    }
}