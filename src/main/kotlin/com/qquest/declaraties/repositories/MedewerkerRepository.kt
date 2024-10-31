package com.qquest.declaraties.repositories

import com.qquest.declaraties.domain.entities.MedewerkerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedewerkerRepository: JpaRepository<MedewerkerEntity, Long?> {
    fun findByProviderId(providerId: Int): MedewerkerEntity?
}