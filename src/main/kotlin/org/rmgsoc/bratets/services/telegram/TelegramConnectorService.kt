package org.rmgsoc.bratets.services.telegram

import org.rmgsoc.bratets.services.modules.TelegramTextMessageHandler

interface TelegramConnectorService {
    fun addTextMessageHandler(handler: TelegramTextMessageHandler)

    fun sendTextMessageToChat(chatId: Long, message: String)
}