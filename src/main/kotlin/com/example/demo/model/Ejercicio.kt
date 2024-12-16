package com.example.demo.model



import jakarta.persistence.*

@Entity
@Table(name = "ejercicio")
data class Ejercicio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nombre: String = "",

    @Column(nullable = false, length = 500)
    val descripcion: String = "",

    @Column(nullable = false)
    val categoria: String = "", // Por ejemplo: Fuerza, Cardio, etc.

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    val plan: PlanEntrenamiento
)



