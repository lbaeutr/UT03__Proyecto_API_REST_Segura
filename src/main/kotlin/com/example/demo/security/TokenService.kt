package com.example.demo.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.Date

@Service
class TokenService {

    @Autowired
    private lateinit var jwtEncoder: JwtEncoder

    /**
     * Genera un token JWT basado en la autenticación proporcionada.
     *
     * @param authentication Información de autenticación del usuario.
     * @return Token JWT como String.
     */
    fun generarToken(authentication: Authentication): String {
        // Obtener los roles del usuario y convertirlos a un formato único
        val roles: String = authentication.authorities
            .map { it.authority }
            .joinToString(" ")

        // Crear los claims del JWT
        val payload = JwtClaimsSet.builder()
            .issuer("self") // Emisor del token
            .issuedAt(Instant.now()) // Hora de emisión
            .expiresAt(Date().toInstant().plus(Duration.ofHours(1))) // Expiración en 1 hora
            .subject(authentication.name) // Usuario autenticado
            .claim("roles", roles) // Roles del usuario
            .build()

        // Generar el token firmándolo con la clave privada
        return jwtEncoder.encode(JwtEncoderParameters.from(payload)).tokenValue
    }
}
