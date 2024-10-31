package com.qquest.declaraties.domain.dto


data class MedewerkerDto(
    val id: Long?,
    val name: String,
    val bankrekening: String?,
    val providerId: Int
)
