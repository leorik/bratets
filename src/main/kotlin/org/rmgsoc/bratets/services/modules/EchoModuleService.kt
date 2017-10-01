package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.rmgsoc.bratets.services.telegram.TelegramTextMessageHandler
import org.springframework.stereotype.Service

@Service
class EchoModuleService(
        telegramConnectorService: TelegramConnectorService
) {
    init {
        telegramConnectorService.addTextMessageHandler(object : TelegramTextMessageHandler{
            override fun isRelevant(text: String): Boolean {
                return true
            }

            override fun processMessage(text: String, from: Long) {
                telegramConnectorService.sendTextMessageToChat(from, text)
            }

        })
    }
}