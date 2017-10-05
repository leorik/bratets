package org.rmgsoc.bratets.models.db

import java.time.Instant
import javax.persistence.*

@Entity
data class Bro (
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column
        var id: Long = 0,

        @Column
        var telegramId : Long = 0,

        @Column
        var name: String = "",

        @Column
        var since: Instant = Instant.MIN)

@Entity
data class BroMessage(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column
        var id: Long = 0,

        @Column
        var telegramId: Long = 0,

        @Column
        var chatId: Long = 0,

        @ManyToOne
        @JoinColumn
        var author : Bro = Bro(),

        @Column
        var time: Instant = Instant.MIN
)