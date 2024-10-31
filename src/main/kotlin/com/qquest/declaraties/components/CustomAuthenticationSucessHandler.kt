package com.qquest.declaraties.components

import com.qquest.declaraties.domain.CustomOAuthMedewerker
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomOAuth2SuccessHandler: AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuthMedewerker = authentication.principal as CustomOAuthMedewerker

        if (oAuthMedewerker.medewerker.bankrekening == null) {
            response.sendRedirect("/register")
        } else {
            response.sendRedirect("/")
        }
    }
}