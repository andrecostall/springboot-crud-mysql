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
        try{
            List<Usuario> userList = this.usuarioService.listaTodosUsuarios();
            if (!userList.isEmpty()) {
                map.put("status", 1);
                map.put("httpStatus", HttpStatus.OK.value());
                map.put("data", userList);
                return new ResponseEntity<>(map, HttpStatus.OK);
            } else {
                map.clear();
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Dados não encontrados");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/usuario")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> valida = usuarioService.buscarPorLogin(usuario.getLogin());
            if (valida.isEmpty()) {
                usuarioService.salvar(usuario);
                map.put("status", 1);
                map.put("httpStatus", HttpStatus.CREATED.value());
                map.put("message", "O registro foi salvo com sucesso!");
                return new ResponseEntity<>(map, HttpStatus.CREATED);
            } else {
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Login já existente!");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> buscaUsuarioPorId(@PathVariable String id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if(usuario.isPresent()) {
                map.put("status", 1);
                map.put("httpStatus", HttpStatus.OK.value());
                map.put("data", usuario);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.clear();
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Dados não encontrados");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deletaUsuario(@PathVariable String id) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if(usuario.isPresent()) {
                usuarioService.deletar(usuario.get());
                map.put("status", 1);
                map.put("httpStatus", HttpStatus.OK.value());
                map.put("message", "Registro deletado com sucesso!!");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else {
                map.clear();
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Dados não encontrados");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/usuario/{email}/email")
    public ResponseEntity<?> buscaUsuarioPorEmail(@PathVariable String email) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
            if(usuario.isPresent()) {
                map.put("status", 1);
                map.put("httpStatus", HttpStatus.OK.value());
                map.put("data", usuario.get());
                return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.clear();
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Dados não encontrados");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<?> atualizaUsuario(@PathVariable String id, @RequestBody Usuario usuarioData) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        try {
            Optional<Usuario> usuario = usuarioService.buscarPorId(id);
            if(usuario.isPresent()) {
                Usuario _usuario = usuario.get();
                    _usuario.setNome(usuarioData.getNome());
                    _usuario.setSenha(usuarioData.getSenha());
                    _usuario.setEmail(usuarioData.getEmail());
                    _usuario.setTelefone(usuarioData.getTelefone());
                    _usuario.setCidade(usuarioData.getCidade());
                    usuarioService.salvar(_usuario);
                    map.put("status", 1);
                    map.put("httpStatus", HttpStatus.OK.value());
                    map.put("message", "Registro atualizado com sucesso!!");
                    return new ResponseEntity<>(map, HttpStatus.OK);
            }else{
                map.clear();
                map.put("status", 0);
                map.put("httpStatus", HttpStatus.NOT_FOUND.value());
                map.put("message", "Dados não encontrados");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("httpStatus", HttpStatus.EXPECTATION_FAILED.value());
            map.put("message", "Falha no Sistema");
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);
        }
    }

}
