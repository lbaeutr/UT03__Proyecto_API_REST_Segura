package com.example.demo.service


import com.example.demo.exception.ResourceNotFoundException
import com.example.demo.model.Usuario
import com.example.demo.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
@Service
class UsuarioService : UserDetailsService {

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun findById(id: Long): Usuario? {
        return usuarioRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Usuario con id $id no encontrado") }
    }

    fun save(usuario: Usuario): Usuario {
        val encodedPassword = passwordEncoder.encode(usuario.password)
        val usuarioConPasswordCodificada = usuario.copy(password = encodedPassword)
        return usuarioRepository.save(usuarioConPasswordCodificada)
    }

    fun deleteById(id: Long) {
        usuarioRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val usuario = usuarioRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("Usuario no encontrado con el nombre: $username")

        return User(usuario.username, usuario.password, emptyList())
    }


}
