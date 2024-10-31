package com.qquest.declaraties

import com.qquest.declaraties.domain.MedewerkerUpdateReq
import com.qquest.declaraties.domain.dto.DeclaratieSummaryDto
import com.qquest.declaraties.domain.dto.MedewerkerDto
import com.qquest.declaraties.domain.dto.MedewerkerSummaryDto
import com.qquest.declaraties.domain.dto.MedewerkerUpdateReqDto
import com.qquest.declaraties.domain.entities.MedewerkerEntity

fun testMedewerkerDtoA(id: Long? = null) = MedewerkerDto(
    id = id,
    name = "VT",
    bankrekening = "NL94ABNA"
)

fun testMedewerkerEntityA(id: Long? = null) = MedewerkerEntity(
    id = id,
    name = "VT",
    bankrekening = "NL94ABNA"
)

fun testMedewerkerEntityB(id: Long? =null) = MedewerkerEntity(
    id=id,
    name= "VT",
    bankrekening = "NL98"
)

fun testMedewerkerEntityC(id: Long? =null) = MedewerkerUpdateReq(
    id=id,
    name= "VT",
    bankrekening = "NL98"
)

fun testMedewerkerDtoB(id: Long? = null) = MedewerkerUpdateReqDto(
    id = id,
    name = "VT1",
    bankrekening = "NL94ABNA"
)

fun testMedewerkerSummaryDtoA(id:Long) = MedewerkerSummaryDto (
    id = id,
    name = "VT"
)

fun testDeclaratieSummaryDtoA(id: Long?=null, medewerkerSummaryDto: MedewerkerSummaryDto) = DeclaratieSummaryDto(
    id = id,
    aankoopDatum = "20-10-2024",
    aankoopOrg = "bol.com",
    bedrag = 100.0,
    datum = "30-10-2014",
    medewerkerSummaryDto = medewerkerSummaryDto
)
