package com.qquest.declaraties.services.impl

import com.qquest.declaraties.domain.MedewerkerUpdateReq
import com.qquest.declaraties.domain.entities.MedewerkerEntity
import com.qquest.declaraties.repositories.MedewerkerRepository
import com.qquest.declaraties.services.MedewerkerService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MedewerkerServiceImpl(private val medewerkerRepository: MedewerkerRepository) : MedewerkerService {
    override fun create(medewerkerEntity: MedewerkerEntity): MedewerkerEntity {
        require(medewerkerEntity.id == null)
        return medewerkerRepository.save(medewerkerEntity)
    }

    override fun list(): List<MedewerkerEntity> {
        return medewerkerRepository.findAll()
    }

    override fun get(id: Long): MedewerkerEntity? {
        return medewerkerRepository.findByIdOrNull(id)
    }

    @Transactional
    override fun fullUpdate(id: Long, medewerkerEntity: MedewerkerEntity): MedewerkerEntity {
        check(medewerkerRepository.existsById(id))
        val normalizedMedewerker = medewerkerEntity.copy(id = id)
        return medewerkerRepository.save(normalizedMedewerker)
    }

    @Transactional
    override fun partialUpdate(id: Long, medewerkerUpdateReq: MedewerkerUpdateReq): MedewerkerEntity {
        val existingMedewerker = medewerkerRepository.findByIdOrNull(id)
        checkNotNull(existingMedewerker)

        val updatedMedewerker = existingMedewerker.copy(
            name = medewerkerUpdateReq.name ?: existingMedewerker.name,
            bankrekening = medewerkerUpdateReq.bankrekening ?: existingMedewerker.bankrekening
        )

        return medewerkerRepository.save(updatedMedewerker)
    }

    override fun delete(id: Long) {
        medewerkerRepository.deleteById(id)
    }
}