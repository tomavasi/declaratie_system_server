package com.qquest.declaraties.services

import com.qquest.declaraties.domain.DeclaratieSummary
import com.qquest.declaraties.domain.DeclaratieSummaryUpdate
import com.qquest.declaraties.domain.entities.DeclaratieEntity

interface DeclaratieService {
    fun create(declaratieSummary: DeclaratieSummary): DeclaratieEntity

    fun fullUpdate(id: Long, declaratieSummary: DeclaratieSummary): DeclaratieEntity

    fun list(): List<DeclaratieEntity>

    fun get(id: Long): DeclaratieEntity?

    fun partialUpdate(id: Long, declaratieSummaryUpdate: DeclaratieSummaryUpdate): DeclaratieEntity

    fun delete(id: Long)
}