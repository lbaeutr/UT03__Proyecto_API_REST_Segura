package com.example.demo.security

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() } // Deshabilitar CSRF si no es necesario (por ejemplo, para APIs REST)
            .authorizeHttpRequests { auth ->
                auth
                    // Endpoints públicos para login y registro
                    .requestMatchers(HttpMethod.POST, "/usuarios/login", "/usuarios/register").permitAll()

                    // Permitir a ENTRENADORES crear, modificar y eliminar planes
                    .requestMatchers(HttpMethod.POST, "/planes").hasRole("ENTRENADOR")
                    .requestMatchers(HttpMethod.PUT, "/planes/{id}").hasRole("ENTRENADOR")
                    .requestMatchers(HttpMethod.DELETE, "/planes/{id}").hasRole("ENTRENADOR")

                    // Permitir a ENTRENADORES administrar ejercicios
                    .requestMatchers(HttpMethod.POST, "/ejercicios").hasRole("ENTRENADOR")
                    .requestMatchers(HttpMethod.PUT, "/ejercicios/{id}").hasRole("ENTRENADOR")
                    .requestMatchers(HttpMethod.DELETE, "/ejercicios/{id}").hasRole("ENTRENADOR")

                    // Permitir a CLIENTES y ENTRENADORES acceder a planes y ejercicios (solo lectura)
                    .requestMatchers(HttpMethod.GET, "/planes").authenticated()
                    .requestMatchers(HttpMethod.GET, "/planes/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET, "/ejercicios").authenticated()
                    .requestMatchers(HttpMethod.GET, "/ejercicios/{id}").authenticated()

                    // Control de acceso para usuarios
                    .requestMatchers(HttpMethod.GET, "/usuarios/{id}").authenticated() // Acceso a sus propios datos
                    .requestMatchers(HttpMethod.POST, "/usuarios/crear").hasRole("ENTRENADOR") // Solo ENTRENADORES pueden crear usuarios

                    // Asegurar que cualquier otra solicitud esté autenticada
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt(Customizer.withDefaults())
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .httpBasic(Customizer.withDefaults())
            .build()
    }


    @Bean
    fun jwtEncoder(rsaKeysProperties: RSAKeysProperties): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaKeysProperties.publicKey)
            .privateKey(rsaKeysProperties.privateKey)
            .build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun jwtDecoder(rsaKeysProperties: RSAKeysProperties): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaKeysProperties.publicKey).build()
    }
}
