package org.tavioribeiro.adb_manager.feature_auth.data.mappers

import org.tavioribeiro.adb_manager.feature_auth.data.remote.dto.LoginResponseDto
import org.tavioribeiro.adb_manager.feature_auth.domain.model.UserSession


fun LoginResponseDto.toDomain(): UserSession {
    return UserSession(
        userId = this.data.user.id,
        userName = this.data.user.name,
        establishmentId = this.data.session.establishmentId,
        sessionId = this.data.session.id,
        accessToken = this.data.user.accessToken
    )
}