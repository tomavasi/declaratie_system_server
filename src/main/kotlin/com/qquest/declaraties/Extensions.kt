package com.qquest.declaraties

import com.qquest.declaraties.domain.DeclaratieSummary
import com.qquest.declaraties.domain.DeclaratieSummaryUpdate
import com.qquest.declaraties.domain.MedewerkerSummary
import com.qquest.declaraties.domain.MedewerkerUpdateReq
import com.qquest.declaraties.domain.dto.*
import com.qquest.declaraties.domain.entities.DeclaratieEntity
import com.qquest.declaraties.domain.entities.MedewerkerEntity

fun MedewerkerEntity.toMedewerkerDto() = MedewerkerDto(
    id = this.id,
    name = this.name,
    bankrekening = this.bankrekening,
    providerId = this.providerId
)

fun MedewerkerDto.toMedewerkerEntity() = MedewerkerEntity(
    id = this.id,
    name = this.name,
    bankrekening = this.bankrekening,
    providerId = this.providerId
)

fun MedewerkerUpdateReqDto.toMedewerkerUpdateReq() = MedewerkerUpdateReq (
    name = this.name,
    bankrekening = this.bankrekening
)

fun DeclaratieSummary.toDeclaratieEntity(medewerker: MedewerkerEntity) = DeclaratieEntity(
    id = this.id,
    datum = this.datum,
    aankoopDatum = this.aankoopDatum,
    aankoopOrg = this.aankoopOrg,
    bedrag = this.bedrag,
    medewerkerEntity = medewerker
)

fun MedewerkerSummaryDto.toMedewerkerSummary() = MedewerkerSummary (
    id = this.id,
    name = this.name
)

fun MedewerkerSummary.toMedewerkerSummaryDto() = MedewerkerSummaryDto (
    id = this.id,
    name = this.name
)

fun MedewerkerEntity.toMedewerkerSummaryDto(): MedewerkerSummaryDto {
    val authorId = this.id
    checkNotNull(authorId)
    return MedewerkerSummaryDto(
        id = authorId,
        name = this.name
    )
}


fun DeclaratieSummaryDto.toDeclaratieSummary() = DeclaratieSummary(
    id = this.id,
    datum = this.datum,
    aankoopDatum = this.aankoopDatum,
    aankoopOrg = this.aankoopOrg,
    bedrag = this.bedrag,
    medewerkerSummary = this.medewerkerSummaryDto.toMedewerkerSummary()
)
fun DeclaratieSummary.toDeclaratieSummaryDto() = DeclaratieSummaryDto(
    id = this.id,
    datum = this.datum,
    aankoopDatum = this.aankoopDatum,
    aankoopOrg = this.aankoopOrg,
    bedrag = this.bedrag,
    medewerkerSummaryDto = this.medewerkerSummary.toMedewerkerSummaryDto()
)

fun DeclaratieEntity.toDeclaratieSummaryDto() = DeclaratieSummaryDto(
    id = this.id,
    datum = this.datum,
    aankoopDatum = this.aankoopDatum,
    aankoopOrg = this.aankoopOrg,
    bedrag = this.bedrag,
    medewerkerSummaryDto = this.medewerkerEntity.toMedewerkerSummaryDto()
)

fun DeclaratieSummaryUpdateDto.toDeclaratieUpdateSummary() = DeclaratieSummaryUpdate(
    datum = this.datum,
    aankoopDatum = this.aankoopDatum,
    aankoopOrg = this.aankoopOrg,
    bedrag = this.bedrag
)