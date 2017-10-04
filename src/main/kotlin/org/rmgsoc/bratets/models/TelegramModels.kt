package org.rmgsoc.bratets.models

data class TextMessage(
        var chatId: Long,
        var author: TelegramUser,
        val text : String
)

data class TelegramUser(
        var id: Long,
        var handle: String
)