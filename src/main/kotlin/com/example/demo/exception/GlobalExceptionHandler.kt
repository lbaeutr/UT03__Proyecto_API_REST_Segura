package com.example.demo.exception



import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // Manejar ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf("error" to "Recurso no encontrado", "message" to (ex.message ?: "Sin detalles").toString())
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    // Manejar ValidationException
    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf("error" to "Validación fallida", "message" to (ex.message ?: "Sin detalles"))
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Manejar otras excepciones genéricas
    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, String>> {
        val errorResponse = mapOf("error" to "Error interno", "message" to (ex.message ?: "Sin detalles").toString())
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}