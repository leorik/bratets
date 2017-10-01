package org.rmgsoc.bratets.services.modules

import org.rmgsoc.bratets.services.telegram.TelegramConnectorService
import org.rmgsoc.bratets.services.telegram.TelegramTextMessageHandler
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class EchoModuleService(
        val telegramConnectorService: TelegramConnectorService
) : TelegramTextMessageHandler{

//    @PostConstruct - disabled
    fun setup() {
        telegramConnectorService.addTextMessageHandler(this)
    }

    override fun isRelevant(text: String): Boolean {
        return true
    }

    override fun processMessage(text: String, from: Long) {
        telegramConnectorService.sendTextMessageToChat(from, text)
    }
}