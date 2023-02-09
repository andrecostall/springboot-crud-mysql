package com.example.springbootcrudmysql.controller;

import com.example.springbootcrudmysql.entity.Usuario;
import com.example.springbootcrudmysql.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/usuarios")
    public ResponseEntity<?> listaTodosUsuarios() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<Usuario> userList = this.usuarioService.listaTodosUsuarios();
        if (!userList.isEmpty()) {
            map.put("status", 1);
            map.put("data", userList);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            map.clear();
            map.put("status", 0);
            map.put("message", "Dados não encontrados");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> salvarUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Optional<Usuario> valida = this.usuarioService.buscarPorLogin(usuario.getLogin());
        if(valida.isEmpty()) {
            usuarioService.salvar(usuario);
            map.put("status", 1);
            map.put("message", "O registro foi salvo com sucesso!");
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        }else{
            map.put("status", 0);
            map.put("message", "Login já existente!");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> buscaUsuarioPorId(@PathVariable String id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            map.put("status", 1);
            map.put("data", usuario);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Dados não encontrados");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deletaUsuario(@PathVariable String id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            usuarioService.deletar(usuario.get());
            map.put("status", 1);
            map.put("message", "Registro deletado com sucesso!!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Dados não encontrados");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/usuario/{email}/email")
    public ResponseEntity<?> buscaUsuarioPorEmail(@PathVariable String email) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
            map.put("status", 1);
            map.put("data", usuario.get());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Dados não encontrados");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> atualizaUsuario(@PathVariable String id, @RequestBody Usuario usuarioDetail) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            usuario.get().setNome(usuarioDetail.getNome());
            usuario.get().setLogin(usuarioDetail.getLogin());
            usuario.get().setSenha(usuarioDetail.getSenha());
            usuario.get().setEmail(usuarioDetail.getEmail());
            usuario.get().setTelefone(usuarioDetail.getTelefone());
            usuario.get().setCidade(usuarioDetail.getCidade());
            usuarioService.salvar(usuario.get());
            map.put("status", 1);
            map.put("message", "Registro atualizado com sucesso!!");
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Dados não encontrados");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

}