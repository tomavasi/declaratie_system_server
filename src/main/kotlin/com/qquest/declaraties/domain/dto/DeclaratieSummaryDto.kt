package com.qquest.declaraties.domain.dto

data class DeclaratieSummaryDto (
    val id: Long?,
    val aankoopOrg: String,
    val aankoopDatum: String,
    val bedrag: Double,
    val datum: String,
    val medewerkerSummaryDto: MedewerkerSummaryDto
)