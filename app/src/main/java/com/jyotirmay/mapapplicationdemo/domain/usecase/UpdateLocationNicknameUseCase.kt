package com.jyotirmay.mapapplicationdemo.domain.usecase

import com.jyotirmay.mapapplicationdemo.domain.model.LocationPoint
import javax.inject.Inject

class UpdateLocationNicknameUseCase @Inject constructor() {

    operator fun invoke(locationPoint: LocationPoint, nickname: String): LocationPoint {
        val trimmed = nickname.trim().take(MAX_NICKNAME_LENGTH)
        val normalizedNickname = trimmed.takeIf { it.isNotBlank() }
        return locationPoint.copy(nickname = normalizedNickname)
    }

    private companion object {
        const val MAX_NICKNAME_LENGTH = 20
    }
}
