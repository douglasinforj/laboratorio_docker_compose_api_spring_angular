package com.lab.api.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/usuarios")
@RequiredArgsConstructor 
@CrossOrigin(origins = "*")           //libera acesso a qualquer frontend
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<UsuarioDTO.Response> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public UsuarioDTO.Response buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping 
    public ResponseEntity<UsuarioDTO.Response> criar(@Valid @RequestBody UsuarioDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public UsuarioDTO.Response atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO.Request dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
