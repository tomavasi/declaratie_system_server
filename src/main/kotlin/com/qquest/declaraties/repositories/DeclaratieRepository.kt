package com.qquest.declaraties.repositories

import com.qquest.declaraties.domain.entities.DeclaratieEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeclaratieRepository: JpaRepository<DeclaratieEntity, Long?> {
}