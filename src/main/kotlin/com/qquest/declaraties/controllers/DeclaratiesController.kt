package com.qquest.declaraties.controllers

import com.qquest.declaraties.domain.dto.DeclaratieSummaryDto
import com.qquest.declaraties.domain.dto.DeclaratieSummaryUpdateDto
import com.qquest.declaraties.domain.entities.DeclaratieEntity
import com.qquest.declaraties.services.DeclaratieService
import com.qquest.declaraties.toDeclaratieSummary
import com.qquest.declaraties.toDeclaratieSummaryDto
import com.qquest.declaraties.toDeclaratieUpdateSummary
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/v1/declaraties"])
class DeclaratiesController(private val declaratieService: DeclaratieService) {

    @PostMapping
    fun createDeclaratie(@RequestBody declaratieSummaryDto: DeclaratieSummaryDto): ResponseEntity<DeclaratieSummaryDto> {
        return try {
            val created = declaratieService.create(declaratieSummaryDto.toDeclaratieSummary()).toDeclaratieSummaryDto()
            ResponseEntity(created, HttpStatus.CREATED)
        } catch (ex: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping(path = ["/{id}"])
    fun fullUpdateDeclaratie(
        @PathVariable("id") id: Long,
        @RequestBody declaratieSummaryDto: DeclaratieSummaryDto
    ): ResponseEntity<DeclaratieSummaryDto> {
        return try {
            val updatedDeclaratie =
                declaratieService.fullUpdate(id, declaratieSummaryDto.toDeclaratieSummary()).toDeclaratieSummaryDto()
            ResponseEntity(updatedDeclaratie, HttpStatus.OK)
        } catch (ex: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path = ["/{id}"])
    fun partialUpdateDeclaratie(
        @PathVariable("id") id: Long,
        @RequestBody declaratieSummaryUpdateDto: DeclaratieSummaryUpdateDto
    ): ResponseEntity<DeclaratieEntity> {
        return try {
            val updatedDeclaratie =
                declaratieService.partialUpdate(id, declaratieSummaryUpdateDto.toDeclaratieUpdateSummary())
            ResponseEntity(updatedDeclaratie, HttpStatus.OK)
        } catch (ex: IllegalStateException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping()
    fun readManyDeclaraties(): List<DeclaratieSummaryDto> {
        return declaratieService.list().map {
            it.toDeclaratieSummaryDto()
        }
    }

    @GetMapping(path = ["/{id}"])
    fun readOneDeclaratie(@PathVariable("id") id: Long): ResponseEntity<DeclaratieSummaryDto> {
        val foundDeclaratie = declaratieService.get(id)?.toDeclaratieSummaryDto()
        return foundDeclaratie?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping(path = ["/{id}"])
    fun deleteDeclaratie(@PathVariable("id") id: Long): ResponseEntity<Unit>{
        declaratieService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}