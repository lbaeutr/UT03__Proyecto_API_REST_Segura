package com.example.demo.service



import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Ejercicio
import com.example.demo.repository.EjercicioRepository
import org.springframework.stereotype.Service

@Service
class EjercicioService(private val ejercicioRepository: EjercicioRepository) {


    //todo

    fun findAll(): List<Ejercicio> {
        return ejercicioRepository.findAll()
    }

    fun findById(id: Long): Ejercicio? {
        return ejercicioRepository.findById(id).orElseThrow { ResourceNotFoundException("Ejercicio con id $id no encontrado") }
    }

    fun save(ejercicio: Ejercicio): Ejercicio {
        return ejercicioRepository.save(ejercicio)
    }

    fun deleteById(id: Long) {
        ejercicioRepository.deleteById(id)
    }
}