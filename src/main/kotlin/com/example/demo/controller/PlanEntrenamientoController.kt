package com.example.demo.controller



import com.example.demo.model.PlanEntrenamiento
import com.example.demo.service.PlanEntrenamientoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/planes")
class PlanEntrenamientoController(private val planEntrenamientoService: PlanEntrenamientoService) {

    @GetMapping
    fun getAllPlanes(): List<PlanEntrenamiento> {
        return planEntrenamientoService.findAll()
    }

    @GetMapping("/{id}")
    fun getPlanById(@PathVariable id: Long): ResponseEntity<PlanEntrenamiento> {
        val plan = planEntrenamientoService.findById(id)
        return if (plan != null) {
            ResponseEntity.ok(plan)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPlan(@RequestBody plan: PlanEntrenamiento): PlanEntrenamiento {
        return planEntrenamientoService.save(plan)
    }

    @PutMapping("/{id}")
    fun updatePlan(@PathVariable id: Long, @RequestBody plan: PlanEntrenamiento): ResponseEntity<PlanEntrenamiento> {
        val existingPlan = planEntrenamientoService.findById(id)
        return if (existingPlan != null) {
            val updatedPlan = plan.copy(id = id) // Creamos una copia con el mismo ID como ha pasado en usuario controller
            ResponseEntity.ok(planEntrenamientoService.save(updatedPlan))
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @DeleteMapping("/{id}")
    fun deletePlan(@PathVariable id: Long): ResponseEntity<Void> {
        return if (planEntrenamientoService.findById(id) != null) {
            planEntrenamientoService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}