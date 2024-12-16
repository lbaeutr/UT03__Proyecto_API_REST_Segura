package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import com.example.demo.security.RSAKeysProperties

@SpringBootApplication
@EnableConfigurationProperties(RSAKeysProperties::class)
class EntrenamientosPlanificadosApplication

fun main(args: Array<String>) {
	runApplication<EntrenamientosPlanificadosApplication>(*args)
}
