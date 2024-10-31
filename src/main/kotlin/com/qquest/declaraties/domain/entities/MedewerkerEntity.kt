package com.qquest.declaraties.domain.entities

import jakarta.persistence.*

@Entity
@Table(name= "medewerkers")
data class MedewerkerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medewerker_id_seq")
    val id: Long?,
    val name:String,
    val bankrekening: String?,
    @Column(name = "provider_id")
    val providerId: Int
)