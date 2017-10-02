package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class BratetsModuleService(
        val telegramConnectorService: TelegramConnectorService
) : TextMessageHandlerModule {

    private var lastBrattsyTime = Instant.EPOCH

    override fun setup() {
        telegramConnectorService.addTextMessageHandler(this)
    }

    override fun isRelevant(text: String): Boolean {
        return normalize(text) == "братцы"
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