package com.qquest.declaraties.domain

data class MedewerkerUpdateReq(
    val id: Long?=null,
    val name: String?=null,
    val bankrekening: String?=null
)
