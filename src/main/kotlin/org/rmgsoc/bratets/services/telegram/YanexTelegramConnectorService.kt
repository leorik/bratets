package org.rmgsoc.bratets.services.telegram

import okhttp3.logging.HttpLoggingInterceptor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.yanex.telegram.TelegramBot
import org.yanex.telegram.entities.Update
import org.yanex.telegram.handler.AbstractUpdateVisitor
import org.yanex.telegram.handler.UpdateVisitor
import org.yanex.telegram.handler.VisitorUpdateHandler
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import kotlin.concurrent.thread

@Service
@Profile("prod")
class YanexTelegramConnectorService(
        telegramProperties : TelegramProperties
): TelegramConnectorService, DisposableBean {
    private val logger  = LoggerFactory.getLogger(this.javaClass)

    private val handlers : MutableSet<TelegramTextMessageHandler> = mutableSetOf()

    private lateinit var handlersPool : ThreadPoolExecutor
    private lateinit var checkupThread : Thread

    private val updateHandler : UpdateVisitor = object : AbstractUpdateVisitor() {
        override fun visitUpdate(update: Update) {
            logger.debug("Received update ${update.updateId}, queueing for processing")

            try {
                handlersPool.execute({ handleUpdate(handlers, update) })

                logger.debug("Update ${update.updateId} queued successfully")
            } catch (ex: Exception) {
                logger.error("Error queueing update ${update.updateId}", ex)
            }
        }
    }

    private val bot : TelegramBot = TelegramBot.create(token = telegramProperties.key!!,
            logLevel = HttpLoggingInterceptor.Level.BASIC)

    @PostConstruct
    fun setup() {
        logger.debug("Initializing telegram service")

        checkupThread = thread(
                start = true,
                name = "Telegram checkup",
                block = { -> bot.listen(0, VisitorUpdateHandler(updateHandler)) }
        )

        logger.debug("Telegram checkup thread initialized")

        handlersPool = ThreadPoolExecutor(
                1,
                10,
                1,
                TimeUnit.MINUTES,
                LinkedBlockingQueue<Runnable>()
        )

        logger.debug("Telegram update handler pool initialized")
    }

    override fun addTextMessageHandler(handler: TelegramTextMessageHandler) {
        handlers.add(handler)
        logger.debug("Handler ${handler.toString()} added to handler bunch")
    }

    override fun sendTextMessageToChat(chatId: Long, message: String) {
        logger.debug("Sending message \"$message\" to chat with ID = $chatId")

        try {
            val request = bot.sendMessage(chatId, message)
            request.execute()

            logger.debug("Message sent successfully")
        } catch (ex : Exception) {
            logger.error("Error sending message \"$message\" to chat with ID = $chatId", ex)
        }

    }

    override fun destroy() {
        checkupThread.interrupt()
        handlersPool.shutdown()
    }

    private fun handleUpdate(handlers: Set<TelegramTextMessageHandler>, update: Update) {
        logger.debug("Processing update ${update.updateId}")

        try {
            handlers.forEach {
                val text = update.message?.text!!
                if (it.isRelevant(text)) {
                    it.processMessage(text, update.senderId)
                }
            }

            logger.debug("Update ${update.updateId} processed successfully")
        } catch (ex: Exception) {
            logger.error("Error during processing update ${update.updateId}", ex)
        }
    }
}