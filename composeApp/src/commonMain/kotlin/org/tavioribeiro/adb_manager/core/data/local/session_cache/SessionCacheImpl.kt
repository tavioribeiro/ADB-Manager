package org.tavioribeiro.adb_manager.core.data.local.session_cache

import com.russhwolf.settings.Settings
import org.tavioribeiro.adb_manager.feature_main.domain.model.UserSession

class SessionCacheImpl(
    private val settings: Settings
) : SessionCache {

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_ESTABLISHMENT_ID = "establishment_id"
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_ACCESS_TOKEN = "access_token"
    }

    override fun saveSession(session: UserSession) {
        settings.putInt(KEY_USER_ID, session.userId)
        settings.putString(KEY_USER_NAME, session.userName)
        settings.putInt(KEY_ESTABLISHMENT_ID, session.establishmentId)
        settings.putInt(KEY_SESSION_ID, session.sessionId)
        settings.putString(KEY_ACCESS_TOKEN, session.accessToken)
    }

    override fun getSession(): UserSession? {
        val userId = settings.getIntOrNull(KEY_USER_ID)
        val accessToken = getAccessToken()

        if (userId == null || accessToken.isNullOrEmpty()) {
            return null
        }

        return UserSession(
            userId = userId,
            userName = settings.getString(KEY_USER_NAME, ""),
            establishmentId = settings.getInt(KEY_ESTABLISHMENT_ID, -1),
            sessionId = settings.getInt(KEY_SESSION_ID, -1),
            accessToken = accessToken
        )
    }

    override fun getAccessToken(): String? {
        return settings.getStringOrNull(KEY_ACCESS_TOKEN)
    }

    override fun clearSession() {
        settings.clear()
    }
}

