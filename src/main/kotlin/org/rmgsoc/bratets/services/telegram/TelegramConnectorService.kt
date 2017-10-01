package org.rmgsoc.bratets.services.telegram

interface TelegramConnectorService {
    fun addTextMessageHandler(handler: TelegramTextMessageHandler)

    fun sendTextMessageToChat(chatId: Long, message: String)
}