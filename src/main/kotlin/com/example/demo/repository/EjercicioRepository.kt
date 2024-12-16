package com.example.demo.repository



import com.example.demo.model.Ejercicio
import com.example.demo.model.PlanEntrenamiento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EjercicioRepository : JpaRepository<Ejercicio, Long> {
    fun findByPlan(plan: PlanEntrenamiento): List<Ejercicio>
}
