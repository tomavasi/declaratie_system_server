package com.qquest.declaraties.services

import com.qquest.declaraties.domain.MedewerkerUpdateReq
import com.qquest.declaraties.domain.entities.MedewerkerEntity

interface MedewerkerService {
    fun create(medewerkerEntity: MedewerkerEntity): MedewerkerEntity

    fun list(): List<MedewerkerEntity>

    fun get(id: Long): MedewerkerEntity?

    fun fullUpdate(id: Long, medewerkerEntity: MedewerkerEntity): MedewerkerEntity

    fun partialUpdate(id: Long, medewerkerUpdateReq: MedewerkerUpdateReq): MedewerkerEntity

    fun delete(id:Long)
}