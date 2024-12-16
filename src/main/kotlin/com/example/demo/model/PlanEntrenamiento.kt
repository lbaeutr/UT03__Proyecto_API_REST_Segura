package com.example.demo.model

import jakarta.persistence.*


@Entity
@Table(name = "plan_entrenamiento")
data class PlanEntrenamiento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nombre: String = "",

    @Column(nullable = false, length = 500)
    val descripcion: String = "",

    @Column(nullable = false)
    val duracion: Int = 0, // Duración en días o semanas

    @ManyToOne
    @JoinColumn(name = "entrenador_id", nullable = false)
    val entrenador: Usuario
)


// todo tenemos que ver que mas añadir a la clase plan de entrenamiento