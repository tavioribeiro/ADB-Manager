package org.tavioribeiro.adb_manager.feature_main.data.repository

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
// Removido import ExperimentalTime pois não é necessário na versão 0.5.0
import org.tavioribeiro.adb_manager.core.data.local.session_cache.SessionCache
import org.tavioribeiro.adb_manager.core.data.remote.dto.ErrorResponse400
import org.tavioribeiro.adb_manager.core.data.remote.dto.ErrorResponse422
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.tavioribeiro.adb_manager.feature_main.data.mappers.toDomain
import org.tavioribeiro.adb_manager.feature_main.data.remote.AuthApiService
import org.tavioribeiro.adb_manager.feature_main.domain.model.UserSession
import org.tavioribeiro.adb_manager.feature_main.domain.repository.AuthRepository
import kotlin.time.ExperimentalTime

class AuthRepositoryImpl(
    private val apiService: AuthApiService,
    private val sessionCache: SessionCache,
    private val db: AppDatabase
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserSession> {
        return try {
            val loginResponseDto = apiService.login(email, password)

            if (loginResponseDto.response == "error") {
                throw Exception("Erro na resposta da API")
            }

            val userSession = loginResponseDto.toDomain()
            sessionCache.saveSession(userSession)
            Result.success(userSession)

        } catch (e: ClientRequestException) {
            val errorMessage = try {
                when (e.response.status) {
                    HttpStatusCode.BadRequest -> { // 400
                        val errorBody = e.response.body<ErrorResponse400>()
                        errorBody.data ?: "Erro de requisição inválida"
                    }
                    HttpStatusCode.UnprocessableEntity -> { // 422
                        val errorBody = e.response.body<ErrorResponse422>()
                        errorBody.errors?.values?.firstOrNull()?.firstOrNull()
                            ?: errorBody.message
                            ?: "Dados inválidos"
                    }
                    else -> "Erro desconhecido: ${e.response.status.value}"
                }
            } catch (parseException: Exception) {
                "Ocorreu um erro ao conectar com o servidor."
            }

            Result.failure(Exception(errorMessage))

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("Sem conexão ou erro inesperado."))
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun logout(): Result<Unit> {
        return runCatching {
            val session = sessionCache.getSession()

            if (session != null) {
                val now = kotlin.time.Clock.System.now().toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )


                // Formatação manual simples da string (YYYY-MM-DD HH:MM:SS)
                // O toString() padrão coloca um 'T' no meio, a API geralmente prefere espaço
                val datePart = now.date.toString()
                val timePart = "${now.hour.toString().padStart(2, '0')}:${now.minute.toString().padStart(2, '0')}:${now.second.toString().padStart(2, '0')}"
                val dateTimeString = "$datePart $timePart"

                try {
                    apiService.closeSession(
                        userId = session.userId,
                        establishmentId = session.establishmentId,
                        sessionId = session.sessionId,
                        dateTime = dateTimeString
                    )
                } catch (e: Exception) {
                    println("Erro ao fechar sessão na API: ${e.message}")
                }
            }

            // Reseta o Banco de Dados Local
            db.transaction {
                db.appDatabaseQueries.deleteAllVehicles()
                db.appDatabaseQueries.deleteAllPriceTables()
                db.appDatabaseQueries.deleteAllPaymentMethods()
            }

            // Limpa Cache de Sessão
            sessionCache.clearSession()
        }
    }

    override fun getCurrentSession(): UserSession? {
        return sessionCache.getSession()
    }
}