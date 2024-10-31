package com.qquest.declaraties.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.qquest.declaraties.domain.entities.MedewerkerEntity
import com.qquest.declaraties.services.MedewerkerService
import com.qquest.declaraties.testMedewerkerDtoA
import com.qquest.declaraties.testMedewerkerDtoB
import com.qquest.declaraties.testMedewerkerEntityA
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*


private const val MEDEWERKER_BASE_URL= "/v1/medewerkers"

@SpringBootTest
@AutoConfigureMockMvc
class MedewerkerControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val medewerkerService: MedewerkerService
    ) {

    val objectMapper = ObjectMapper()

    @BeforeEach
    fun beforeEach(){
        every {
            medewerkerService.create(any())
        } answers {
            firstArg()
        }
    }

    @Test
    fun `test that create Medewerker saves the Medewerker`(){

        mockMvc.post(MEDEWERKER_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testMedewerkerDtoA()
            )
        }

        val expected = MedewerkerEntity (
            id = null,
            name = "VT",
            bankrekening = "NL94ABNA"
        )

        verify { medewerkerService.create(expected) }
    }

    @Test
    fun `test that create Medewerker return a HTTP 201 status on success`() {
        mockMvc.post(MEDEWERKER_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testMedewerkerDtoA()
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test that create Medewerker returns HTTP 400 when IllegalArgumentEx`(){
        every {
            medewerkerService.create((any()))
        } throws(IllegalStateException())

        mockMvc.post(MEDEWERKER_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testMedewerkerDtoA()
            )
        }.andExpect {
            status { isBadRequest() }
        }
    }


    @Test
    fun `test all list returns an empty list and HTTP 200 when no Medewerkers in database`(){

        every {
            medewerkerService.list()
        } answers {
            emptyList()
        }

        mockMvc.get(MEDEWERKER_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json("[]") }
        }
    }

    @Test
    fun `test that list returns Medewerkers and HTTP 200 when in medewerkers in database`(){
        every {
            medewerkerService.list()
        } answers {
            listOf(testMedewerkerEntityA(1))
        }

        mockMvc.get(MEDEWERKER_BASE_URL){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$[0].id", equalTo(1)) }
            content { jsonPath("$[0].name", equalTo("VT")) }
            content { jsonPath("$[0].bankrekening", equalTo("NL94ABNA")) }
        }
    }

    @Test
    fun `test that get returns HTTP 404 when Medewerker not in db`(){

        every {
            medewerkerService.get((any()))
        } answers {
            null
        }

        mockMvc.get("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `test that HTTP 200 and Medewerker returned when Medewerker found`(){
        every {
            medewerkerService.get((any()))
        } answers {
            testMedewerkerEntityA(id = 999)
        }

        mockMvc.get("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(999)) }
            content { jsonPath("$.name", equalTo("VT")) }
            content { jsonPath("$.bankrekening", equalTo("NL94ABNA")) }
        }
    }

    @Test
    fun `test that full update Medewerker returns HTTP 200 and updates db`(){
        every {
            medewerkerService.fullUpdate(any(),any())
        } answers {
            secondArg()
        }

        mockMvc.put("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testMedewerkerDtoA(999))
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(999)) }
            content { jsonPath("$.name", equalTo("VT")) }
            content { jsonPath("$.bankrekening", equalTo("NL94ABNA")) }
        }
    }

    @Test
    fun `test that full update returns HTTP 400 on IllegalStateEx`(){
        every {
            medewerkerService.fullUpdate(any(),any())
        } throws(IllegalStateException())

        mockMvc.put("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testMedewerkerDtoA(999))
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test that partial update returns HTTP 400 on IllegalStateEx`(){
        every {
            medewerkerService.partialUpdate(any(),any())
        } throws(IllegalStateException())

        mockMvc.patch("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testMedewerkerDtoB(999))
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `test that partial update Medewerker returns HTTP 200 and updates db`(){
        every {
            medewerkerService.partialUpdate(any(),any())
        } answers {
            secondArg()
        }

        mockMvc.patch("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testMedewerkerDtoB(999))
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(999)) }
            content { jsonPath("$.name", equalTo("VT1")) }
            content { jsonPath("$.bankrekening", equalTo("NL94ABNA")) }
        }
    }

    @Test
    fun `test that delete Medewerker returns HTTP 204 and deletes entry from db`(){
        every {
            medewerkerService.delete(any())
        } answers {}

        mockMvc.delete("$MEDEWERKER_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isNoContent() }
        }
    }

}