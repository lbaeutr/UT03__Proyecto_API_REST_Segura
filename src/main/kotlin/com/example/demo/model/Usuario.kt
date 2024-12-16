package com.example.demo.model



import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "usuario")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    var username: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @Column(nullable = false)
    var roles: String? = "USER" // Por defecto CLIENTE o ENTRENADOR
)




// todo tenemos que ver que mas añadir a la clase usuario