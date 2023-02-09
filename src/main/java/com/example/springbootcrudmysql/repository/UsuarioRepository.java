package com.example.springbootcrudmysql.repository;

import com.example.springbootcrudmysql.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    public Usuario findByEmail(String email);
    public Usuario findByLogin(String login);

}
