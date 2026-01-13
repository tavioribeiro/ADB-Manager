package org.tavioribeiro.adb_manager.feature_main.data.mappers

import org.tavioribeiro.adb_manager.feature_main.data.remote.dto.LoginResponseDto
import org.tavioribeiro.adb_manager.feature_main.domain.model.UserSession


fun LoginResponseDto.toDomain(): UserSession {
    return UserSession(
        userId = this.data.user.id,
        userName = this.data.user.name,
        establishmentId = this.data.session.establishmentId,
        sessionId = this.data.session.id,
        accessToken = this.data.user.accessToken
    )
}