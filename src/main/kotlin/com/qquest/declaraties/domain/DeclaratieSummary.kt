package com.qquest.declaraties.domain

data class DeclaratieSummary (
    val id: Long?,
    val aankoopOrg: String,
    val aankoopDatum: String,
    val bedrag: Double,
    val datum: String,
    val medewerkerSummary: MedewerkerSummary
)