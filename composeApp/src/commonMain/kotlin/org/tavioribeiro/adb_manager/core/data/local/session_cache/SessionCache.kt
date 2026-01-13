package org.tavioribeiro.adb_manager.core.data.local.session_cache

import org.tavioribeiro.adb_manager.feature_main.domain.model.UserSession

interface SessionCache {
    fun saveSession(session: UserSession)
    fun getSession(): UserSession?
    fun getAccessToken(): String?
    fun clearSession()
}