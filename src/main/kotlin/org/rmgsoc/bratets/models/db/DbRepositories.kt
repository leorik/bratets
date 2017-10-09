package org.rmgsoc.bratets.models.db

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

interface BroRepository : CrudRepository<Bro, Long> {
    fun getByTelegramId(telegramId: Long) : Bro?
}

interface BroMessageRepository : CrudRepository<BroMessage, Long> {
    fun findFirstByChatIdOrderByTimeDesc(chatId: Long) : BroMessage?
}