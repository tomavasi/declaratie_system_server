package com.qquest.declaraties.services

import com.qquest.declaraties.domain.CustomOAuthMedewerker
import com.qquest.declaraties.domain.entities.MedewerkerEntity
import com.qquest.declaraties.repositories.MedewerkerRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val medewerkerRepository: MedewerkerRepository
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauth2User = delegate.loadUser(userRequest)
        val providerId = oauth2User.attributes["id"] as Int
        val name = oauth2User.attributes["name"] as String

        val medewerker = medewerkerRepository.findByProviderId(providerId) ?: MedewerkerEntity(
            id = null,
            name = name,
            providerId = providerId,
            bankrekening = null
        ).also { medewerkerRepository.save(it) }

        return CustomOAuthMedewerker(oauth2User, medewerker)
    }

}


