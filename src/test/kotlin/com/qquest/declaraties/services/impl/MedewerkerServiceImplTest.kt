package com.qquest.declaraties.services.impl

import com.qquest.declaraties.domain.MedewerkerUpdateReq
import com.qquest.declaraties.domain.entities.MedewerkerEntity
import com.qquest.declaraties.repositories.MedewerkerRepository
import com.qquest.declaraties.testMedewerkerEntityA
import com.qquest.declaraties.testMedewerkerEntityB
import com.qquest.declaraties.testMedewerkerEntityC
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class MedewerkerServiceImplTest @Autowired constructor(
    private val underTest: MedewerkerServiceImpl,
    private val medewerkerRepository: MedewerkerRepository
) {

    @Test
    fun `test that save persists the Medewerker in the database`() {
        val savedMedewerker = underTest.create(testMedewerkerEntityA())
        assertThat(savedMedewerker.id).isNotNull()

        val recalledMedewerker = medewerkerRepository.findByIdOrNull(savedMedewerker.id!!)
        assertThat(recalledMedewerker).isNotNull
        assertThat(recalledMedewerker!!).isEqualTo(
            testMedewerkerEntityA(id = savedMedewerker.id)
        )
    }

    @Test
    fun `test that an Medewerker with an ID throws IllegalArgumentEx`() {
        assertThrows<IllegalArgumentException> {
            val existed = testMedewerkerEntityA(999)
            underTest.create(existed)
        }
    }

    @Test
    fun `test that list returns empty list when no Medewerkers in db`() {
        val result = underTest.list()
        assertThat(result).isEmpty()
    }

    @Test
    fun `test that list returns Medewerkers when Medewerkers in db`() {
        val savedMedewerker = medewerkerRepository.save(testMedewerkerEntityA())
        val expexted = listOf(savedMedewerker)
        val results = underTest.list()
        assertThat(results).isEqualTo(expexted)
    }

    @Test
    fun `test that get returns null when no Medewerker in db`() {
        val result = underTest.get(999)
        assertThat(result).isNull()
    }

    @Test
    fun `test that get returns Medewerker when present in db`() {
        val savedMedewerker = medewerkerRepository.save(testMedewerkerEntityA(999))

        val result = underTest.get(savedMedewerker.id!!)
        assertThat(result).isEqualTo(savedMedewerker)
    }

    @Test
    fun `test that full update successfully updates Medewerker in db`() {
        val saved = medewerkerRepository.save(testMedewerkerEntityA())

        val updated = MedewerkerEntity(
            id = saved.id!!,
            name = "VT",
            bankrekening = "NL98"
        )

        val result = underTest.fullUpdate(saved.id!!, updated)
        assertThat(result).isEqualTo(updated)

        val retrieved = medewerkerRepository.findByIdOrNull(saved.id!!)
        assertThat(retrieved).isNotNull()
        assertThat(retrieved).isEqualTo(updated)
    }

    @Test
    fun `test that full update throws IllegalStateEx when not in db`() {
        assertThrows<IllegalStateException> {
            val updated = testMedewerkerEntityB(999)
            underTest.fullUpdate(999, updated)
        }
    }

    @Test
    fun `test that partial update throws IllegalStateEx when not in db`() {
        assertThrows<IllegalStateException> {
            val updated = testMedewerkerEntityC(999)
            underTest.partialUpdate(999, updated)
        }
    }

    @Test
    fun `test that partial update does not update when all values are null`() {
        val saved = medewerkerRepository.save(testMedewerkerEntityA())
        val update = underTest.partialUpdate(saved.id!!, MedewerkerUpdateReq())
        assertThat(update).isEqualTo(saved)
    }

    @Test
    fun `delete an existing medewerker in db`() {
        val saved = medewerkerRepository.save(testMedewerkerEntityA())
        underTest.delete(saved.id!!)

        assertThat(medewerkerRepository.existsById(saved.id!!)).isFalse()
    }

    @Test
    fun `delete an non existing medewerker in db`() {

        underTest.delete(999)
        assertThat(medewerkerRepository.existsById(999)).isFalse()
    }


}