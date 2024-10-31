package com.qquest.declaraties.services.impl

import com.qquest.declaraties.domain.DeclaratieSummary
import com.qquest.declaraties.domain.DeclaratieSummaryUpdate
import com.qquest.declaraties.domain.entities.DeclaratieEntity
import com.qquest.declaraties.repositories.DeclaratieRepository
import com.qquest.declaraties.repositories.MedewerkerRepository
import com.qquest.declaraties.services.DeclaratieService
import com.qquest.declaraties.toDeclaratieEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeclaratieServiceImpl(
    private val declaratieRepository: DeclaratieRepository,
    private val medewerkerRepository: MedewerkerRepository
) : DeclaratieService {

    @Transactional
    override fun create(declaratieSummary: DeclaratieSummary): DeclaratieEntity {
        require(declaratieSummary.id == null)
        val medewerker = medewerkerRepository.findByIdOrNull(declaratieSummary.medewerkerSummary.id)
        checkNotNull(medewerker)

        return declaratieRepository.save(declaratieSummary.toDeclaratieEntity(medewerker))
    }

    @Transactional
    override fun fullUpdate(id: Long, declaratieSummary: DeclaratieSummary): DeclaratieEntity {
        check(declaratieRepository.existsById(id))
        val normalizedDeclaratie = declaratieSummary.copy(id = id)
        val medewerker = medewerkerRepository.findByIdOrNull(normalizedDeclaratie.medewerkerSummary.id)
        checkNotNull(medewerker)
        return declaratieRepository.save(declaratieSummary.toDeclaratieEntity(medewerker))
    }

    override fun list(): List<DeclaratieEntity> {
        return declaratieRepository.findAll()
    }

    override fun get(id: Long): DeclaratieEntity? {
        return declaratieRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun partialUpdate(id: Long, declaratieSummaryUpdate: DeclaratieSummaryUpdate): DeclaratieEntity {
        val existedDeclaratie = declaratieRepository.findByIdOrNull(id)
        checkNotNull(existedDeclaratie)

        val updatedDeclaratie = existedDeclaratie.copy(
            aankoopDatum = declaratieSummaryUpdate.aankoopDatum ?: existedDeclaratie.aankoopDatum,
            aankoopOrg = declaratieSummaryUpdate.aankoopOrg ?: existedDeclaratie.aankoopOrg,
            bedrag = declaratieSummaryUpdate.bedrag ?: existedDeclaratie.bedrag,
            datum = declaratieSummaryUpdate.datum ?: existedDeclaratie.datum

        )
        return declaratieRepository.save(updatedDeclaratie)
    }

    override fun delete(id: Long) {
        declaratieRepository.deleteById(id)
    }
}