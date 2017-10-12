package org.rmgsoc.bratets.models.db

import org.hibernate.SessionFactory
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.Instant

interface BroRepository : CrudRepository<Bro, Long> {
    fun getByTelegramId(telegramId: Long) : Bro?
}

interface BroMessageRepository : CrudRepository<BroMessage, Long> {
    fun findFirstByChatIdOrderByTimeDesc(chatId: Long) : BroMessage?

    @Query("SELECT bm FROM BroMessage bm WHERE chatId = :chatId AND time > :startDate AND time < :endDate")
    fun findMessagesFromPeriodByChatId(
            @Param("chatId") chatId: Long,
            @Param("startDate") startDate: Instant,
            @Param("endDate") endDate: Instant = Instant.now()) : List<BroMessage>
}