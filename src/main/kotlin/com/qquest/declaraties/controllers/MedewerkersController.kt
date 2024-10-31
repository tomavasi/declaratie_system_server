package com.qquest.declaraties.controllers

import com.qquest.declaraties.domain.dto.MedewerkerDto
import com.qquest.declaraties.domain.dto.MedewerkerUpdateReqDto
import com.qquest.declaraties.services.MedewerkerService
import com.qquest.declaraties.toMedewerkerDto
import com.qquest.declaraties.toMedewerkerEntity
import com.qquest.declaraties.toMedewerkerUpdateReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/medewerkers"])
class MedewerkersController(private val medewerkerService: MedewerkerService) {

    @PostMapping
    fun createMedewerker(@RequestBody medewerkerDto: MedewerkerDto): ResponseEntity<MedewerkerDto> {
        try {
            val createdMedewerker = medewerkerService.create(medewerkerDto.toMedewerkerEntity()).toMedewerkerDto()
            return ResponseEntity(createdMedewerker, HttpStatus.CREATED)
        } catch (ex: IllegalStateException) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }

    @GetMapping
    fun readManyMedewerkers(): List<MedewerkerDto> {
        return medewerkerService.list().map { it.toMedewerkerDto() }
    }

    @GetMapping(path= ["/{id}"])
    fun readOneMedewerker(@PathVariable("id") id: Long): ResponseEntity<MedewerkerDto>{
        val foundMedewerker = medewerkerService.get(id)?.toMedewerkerDto()
        return foundMedewerker?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping(path= ["/{id}"])
    fun fullUpdateMedewerker(@PathVariable("id") id: Long, @RequestBody medewerkerDto: MedewerkerDto): ResponseEntity<MedewerkerDto>{
        return try {
            val updatedMedewerker = medewerkerService.fullUpdate(id, medewerkerDto.toMedewerkerEntity()).toMedewerkerDto()
            ResponseEntity(updatedMedewerker, HttpStatus.OK)
        } catch (ex:IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PatchMapping(path= ["/{id}"])
    fun partialUpdateMedewerker(@PathVariable("id") id: Long, @RequestBody medewerkerUpdateReqDto: MedewerkerUpdateReqDto): ResponseEntity<MedewerkerDto>{
        return try {
            val updatedMedewerker = medewerkerService.partialUpdate(id, medewerkerUpdateReqDto.toMedewerkerUpdateReq()).toMedewerkerDto()
            ResponseEntity(updatedMedewerker, HttpStatus.OK)
        } catch (ex: IllegalStateException){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping(path= ["/{id}"])
    fun deleteMedewerker(@PathVariable("id") id:Long): ResponseEntity<Unit>{
        medewerkerService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}