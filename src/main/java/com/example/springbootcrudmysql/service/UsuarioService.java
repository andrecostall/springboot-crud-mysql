package com.example.springbootcrudmysql.service;

import com.example.springbootcrudmysql.entity.Usuario;
import com.example.springbootcrudmysql.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listaTodosUsuarios(){
        return this.usuarioRepository.findAll();
    }

    public void salvar(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(String id){
        Optional<Usuario> x = this.usuarioRepository.findById(id);
        return x;
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return Optional.ofNullable(usuarioRepository.findByEmail(email));
    }

    public Optional<Usuario> buscarPorLogin(String login){
        return Optional.ofNullable(usuarioRepository.findByLogin(login));
    }

    public void deletar(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

}
