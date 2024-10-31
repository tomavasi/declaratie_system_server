package com.qquest.declaraties.domain.entities

import jakarta.persistence.*

@Entity
@Table(name="declaraties")
data class DeclaratieEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "declaratie_id_seq")
    val id: Long?,
    @Column(name = "aankoop_org")
    val aankoopOrg: String,
    @Column(name = "aankoop_datum")
    val aankoopDatum: String,
    val bedrag: Double,
    val datum: String,
    @ManyToOne(cascade = [CascadeType.DETACH])
    @JoinColumn(name="medewerker_id")
    val medewerkerEntity: MedewerkerEntity
)