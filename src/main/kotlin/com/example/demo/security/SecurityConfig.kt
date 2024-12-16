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

//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        return http
//            //.csrf { csrf -> csrf.disable() }
//            .authorizeHttpRequests { auth -> auth
//                .requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll()
//
//                //.requestMatchers(HttpMethod.GET, "/usuarios").hasRole("CLIENTE")
////                .requestMatchers(HttpMethod.GET, "/usuarios/{id}").authenticated()
////                .requestMatchers(HttpMethod.PUT, "/usuarios/{id}").hasRole("CLIENTE")
////                .requestMatchers(HttpMethod.DELETE, "/usuarios/{id}").hasRole("CLIENTE")
//                .anyRequest().permitAll()
//            }
////            .csrf { csrf -> csrf.disable() }
////            .authorizeHttpRequests { auth -> auth
////                .anyRequest().permitAll()
////            }
//            //.oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
//            //.sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            //.httpBasic(Customizer.withDefaults())
//            .build()
//    }


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            //.cors(Customizer.withDefaults()) // Habilitar CORS
            .csrf { csrf -> csrf.disable() } // Deshabilitar CSRF si es necesario
            .authorizeHttpRequests { auth -> auth
                .requestMatchers(HttpMethod.POST, "/usuarios/login", "/usuarios/register").permitAll()
                .requestMatchers( "/usuarios/listar").hasRole("ENTRENADOR")

                // .requestMatchers("/usuarios/**").permitAll()

//                .requestMatchers(HttpMethod.POST,"/planes").permitAll()
//
//                .requestMatchers(HttpMethod.POST,"/usuarios/login").permitAll()
//                .requestMatchers(HttpMethod.GET, "/usuarios/listar").permitAll()
//                .requestMatchers(HttpMethod.GET, "/usuarios/{id}").permitAll()





                 .anyRequest().authenticated()
            }
           .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
           .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
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
