package com.qquest.declaraties.domain.dto

import com.qquest.declaraties.domain.entities.MedewerkerEntity

data class DeclaratieDto(
    val id: Long?,
    val aankoopOrg: String,
    val aankoopDatum: String,
    val bedrag: Double,
    val datum: String,
    val medewerkerDto: MedewerkerDto
)
