package com.qquest.declaraties.domain

import com.qquest.declaraties.domain.entities.MedewerkerEntity
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component


data class CustomOAuthMedewerker(
    val oauth2User: OAuth2User,
    val medewerker: MedewerkerEntity
) : OAuth2User by oauth2User