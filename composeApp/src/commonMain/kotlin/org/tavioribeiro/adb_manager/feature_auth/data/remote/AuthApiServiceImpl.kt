package org.tavioribeiro.adb_manager.feature_auth.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters
import org.tavioribeiro.adb_manager.feature_auth.data.remote.dto.CloseSessionResponseDto
import org.tavioribeiro.adb_manager.feature_auth.data.remote.dto.LoginResponseDto

class AuthApiServiceImpl(
    private val httpClient: HttpClient
) : AuthApiService {

    override suspend fun login(email: String, password: String): LoginResponseDto {
        return httpClient.submitForm(
            url = "user/login",
            formParameters = Parameters.Companion.build {
                append("email", email)
                append("password", password)
            }
        ).body()
    }

    override suspend fun closeSession(userId: Int, establishmentId: Int, sessionId: Int, dateTime: String): CloseSessionResponseDto {
        return httpClient.submitForm(
            url = "$userId/establishment/$establishmentId/session/close/$sessionId",
            formParameters = Parameters.build {
                append("dateTime", dateTime)
            }
        ).body()
    }
}