package org.tavioribeiro.adb_manager.feature_main.data.repository

import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.tavioribeiro.adb_manager.core.data.local.local_storage.LocalStorage
import org.tavioribeiro.adb_manager.db.AppDatabase
import org.tavioribeiro.adb_manager.feature_main.data.remote.AuthApiService
import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.LoginDataDto
import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.LoginResponseDto
import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.SessionDto
import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.UserDto
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthRepositoryTest {

    private val apiService = mockk<AuthApiService>()
    private val localStorage = mockk<LocalStorage>(relaxed = true)
    private val db = mockk<AppDatabase>(relaxed = true)

    private val repository = AuthRepositoryImpl(apiService, localStorage, db)

    @Test
    fun `login deve salvar a sessao e retornar sucesso quando a API retornar 200`() = runTest {
        val email = "teste@teste.com"
        val password = "123"

        
        val mockResponse = LoginResponseDto(
            response = "success",
            data = LoginDataDto(
                user = UserDto(1, "Jose", "token123"),
                session = SessionDto(100, 4827)
            )
        )

        coEvery { apiService.login(email, password) } returns mockResponse

        val result = repository.login(email, password)

        assertTrue(result.isSuccess, "O main deveria ter ocorrido com sucesso")

        verify { localStorage.saveSession(any()) }
    }

    @Test
    fun `login deve retornar falha quando a API lancar uma excecao`() = runTest {
        coEvery { apiService.login(any(), any()) } throws Exception("Erro 401")

        val result = repository.login("email", "senha")

        assertTrue(result.isFailure, "O main deveria falhar ao receber uma exceção")

        verify(exactly = 0) { localStorage.saveSession(any()) }
    }
}