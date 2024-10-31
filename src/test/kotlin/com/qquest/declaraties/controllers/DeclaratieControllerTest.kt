package com.qquest.declaraties.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.qquest.declaraties.domain.entities.DeclaratieEntity
import com.qquest.declaraties.domain.entities.MedewerkerEntity
import com.qquest.declaraties.services.DeclaratieService
import com.qquest.declaraties.services.MedewerkerService
import com.qquest.declaraties.testDeclaratieSummaryDtoA
import com.qquest.declaraties.testMedewerkerDtoA
import com.qquest.declaraties.testMedewerkerSummaryDtoA
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put


private const val DECLARATIES_BASE_URL = "/v1/declaraties"

@SpringBootTest
@AutoConfigureMockMvc
class DeclaratieControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val medewerkerService: MedewerkerService,
    @MockkBean private val declaratieService: DeclaratieService
) {

    val objectMapper = ObjectMapper()

    val expectedMedewerker = MedewerkerEntity(
        id = 9,
        name = "VT",
        bankrekening = "NL94ABNA"
    )

    val expectedDeclaratie = DeclaratieEntity(
        id = null,
        aankoopDatum = "20-10-2024",
        aankoopOrg = "bol.com",
        bedrag = 100.0,
        datum = "30-10-2014",
        medewerkerEntity = expectedMedewerker
    )

    @BeforeEach
    fun beforeEach() {
        every {
            declaratieService.create(any())
        } answers {
            expectedDeclaratie
        }
    }

    @Test
    fun `test that create returns HTTP 201 when book is created`() {
        mockMvc.post(DECLARATIES_BASE_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                testDeclaratieSummaryDtoA(null, testMedewerkerSummaryDtoA(9))
            )
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `test that full update Medewerker returns HTTP 200 and updates db`(){
        val expectedDeclaratie = DeclaratieEntity(
            id = 999,
            aankoopDatum = "20-10-2024",
            aankoopOrg = "bol.com",
            bedrag = 100.0,
            datum = "30-10-2014",
            medewerkerEntity = expectedMedewerker
        )
        every {
            declaratieService.fullUpdate(any(), any())
        } answers {
            expectedDeclaratie
        }

        mockMvc.put("$DECLARATIES_BASE_URL/999"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(testDeclaratieSummaryDtoA(999, testMedewerkerSummaryDtoA(9)))
        }.andExpect {
            status { isOk() }
            content { jsonPath("$.id", equalTo(999)) }
            content { jsonPath("$.aankoopOrg", equalTo("bol.com")) }
            content { jsonPath("$.aankoopDatum", equalTo("20-10-2024")) }
            content { jsonPath("$.bedrag", equalTo(100.0)) }
            content { jsonPath("$.datum", equalTo("30-10-2014")) }
        }
    }

}