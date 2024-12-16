package com.example.demo.controller

import com.example.demo.model.Usuario
import com.example.demo.security.TokenService
import com.example.demo.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping("/listar")
    fun getAllUsuarios(): List<Usuario> {
        return usuarioService.findAll()
    }

    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: Long): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(id)
        return if (usuario != null) {
            ResponseEntity.ok(usuario)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/crear")
    fun createUsuario(@RequestBody usuario: Usuario): Usuario {
        return usuarioService.save(usuario)
    }




    @PutMapping("/{id}")
    fun updateUsuario(@PathVariable id: Long, @RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val existingUsuario = usuarioService.findById(id)
        return if (usuario.username.isNullOrBlank() || usuario.password.isNullOrBlank()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null)
        } else if (existingUsuario != null) {
            val updatedUsuario = usuario.copy(id = id)
            ResponseEntity.ok(usuarioService.save(updatedUsuario))
        } else {
            ResponseEntity.notFound().build()
        }
    }


    @DeleteMapping("/{id}")
    fun deleteUsuario(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        return if (usuarioService.findById(id) != null) {
            usuarioService.deleteById(id)
            ResponseEntity.ok(mapOf("mensaje" to "Usuario eliminado correctamente"))
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("mensaje" to "Usuario no encontrado"))
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): ResponseEntity<Any> {
        return try {
            if (usuario.username.isNullOrBlank() || usuario.password.isNullOrBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mapOf("mensaje" to "Username y password son requeridos"))
            }

            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(usuario.username, usuario.password)
            )
            val token = tokenService.generarToken(authentication)
            ResponseEntity.ok(mapOf("token" to token))
        } catch (e: AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mapOf("mensaje" to "Credenciales incorrectas"))
        }
    }

    @PostMapping("/register")
    fun register(
        @RequestBody usuario: Usuario
    ): ResponseEntity<Any> {
        return try {
            if (usuario.username.isNullOrBlank() || usuario.password.isNullOrBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mapOf("mensaje" to "Username y password son requeridos"))
            }
            val existingUsuario = usuarioService.loadUserByUsername(usuario.username!!)
            if (existingUsuario != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(mapOf("mensaje" to "El usuario ya existe en la base de datos"))
            }
            val savedUsuario = usuarioService.save(usuario)
            ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("mensaje" to "Error al registrar el usuario"))
        }
    }


}
