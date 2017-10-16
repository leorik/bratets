package org.rmgsoc.bratets.models.db

import org.springframework.data.repository.CrudRepository

interface BroRepository : CrudRepository<Bro, Long> {
    fun getByTelegramId(telegramId: Long) : Bro?
}

interface BroMessageRepository : CrudRepository<BroMessage, Long> {
    fun findFirstByChatIdOrderByTimeDesc(chatId: Long) : BroMessage?
}