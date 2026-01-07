package org.tavioribeiro.adb_manager.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.tavioribeiro.adb_manager.core.data.local.session_cache.SessionCache


object KtorClientFactory {
    fun create(sessionCache: SessionCache): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Ktor Logger: $message")
                    }
                }
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val token = sessionCache.getAccessToken()
                        if (token != null) {
                            BearerTokens(accessToken = token, refreshToken = "")
                        } else {
                            null
                        }
                    }
                }
            }

            defaultRequest {
                //TODO("migrar pra variavel de ambiente")
                url("https://dev.app.com.br/api/")
                headers.append("clientSide", "app")
                contentType(ContentType.Application.Json)
            }

            expectSuccess = true
        }
    }
}
