package com.miguelmialdea.accessnews.data.remote

import com.miguelmialdea.accessnews.core.common.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory for creating Ktor HTTP client
 */
object KtorClientFactory {
    fun create(): HttpClient {
        return HttpClient(Android) {
            // Content negotiation for JSON
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.INFO
            }

            // Engine configuration
            engine {
                connectTimeout = Constants.CONNECT_TIMEOUT.toInt()
                socketTimeout = Constants.NETWORK_TIMEOUT.toInt()
            }
        }
    }
}
