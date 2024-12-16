package com.example.demo.service




import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.PlanEntrenamiento
import com.example.demo.repository.PlanEntrenamientoRepository
import org.springframework.stereotype.Service

@Service
class PlanEntrenamientoService(private val planEntrenamientoRepository: PlanEntrenamientoRepository) {



    //todo

    fun findAll(): List<PlanEntrenamiento> {
        return planEntrenamientoRepository.findAll()
    }

    fun findById(id: Long): PlanEntrenamiento? {
        return planEntrenamientoRepository.findById(id).orElseThrow { ResourceNotFoundException("Plan de entrenamiento con id $id no encontrado") }
    }


    fun save(planEntrenamiento: PlanEntrenamiento): PlanEntrenamiento {
        return planEntrenamientoRepository.save(planEntrenamiento)
    }

    fun deleteById(id: Long) {
        planEntrenamientoRepository.deleteById(id)
    }


}
