package com.example.demo.controller


import com.example.demo.model.Ejercicio
import com.example.demo.service.EjercicioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ejercicios")
class EjercicioController(private val ejercicioService: EjercicioService) {

    @GetMapping
    fun getAllEjercicios(): List<Ejercicio> {
        return ejercicioService.findAll()
    }

    @GetMapping("/{id}")
    fun getEjercicioById(@PathVariable id: Long): ResponseEntity<Ejercicio> {
        val ejercicio = ejercicioService.findById(id)
        return if (ejercicio != null) {
            ResponseEntity.ok(ejercicio)
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @PostMapping
    fun createEjercicio(@RequestBody ejercicio: Ejercicio): ResponseEntity<Ejercicio> {
        val savedEjercicio = ejercicioService.save(ejercicio)
        return ResponseEntity.status(201).body(savedEjercicio)
    }

    @PutMapping("/{id}")
    fun updateEjercicio(@PathVariable id: Long, @RequestBody ejercicio: Ejercicio): ResponseEntity<Ejercicio> {
        val existingEjercicio = ejercicioService.findById(id)
        return if (existingEjercicio != null) {
            val updatedEjercicio =
                ejercicio.copy(id = id) // Creamos una copia con el mismo ID como ha pasado en usuario controller y plan controller
            ResponseEntity.ok(ejercicioService.save(updatedEjercicio))
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @DeleteMapping("/{id}")
    fun deleteEjercicio(@PathVariable id: Long): ResponseEntity<Void> {
        return if (ejercicioService.findById(id) != null) {
            ejercicioService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}