package com.lab.api.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<UsuarioDTO.Response> listar() {
        return repository.findAll()
                .stream()
                .map(UsuarioDTO.Response::from)
                .toList();
    }

    public UsuarioDTO.Response buscarPorId(Long id) {
        return repository.findById(id)
                .map(UsuarioDTO.Response::from)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado"
                ));
    }

    public UsuarioDTO.Response criar(UsuarioDTO.Request dto) {
        if (repository.existsByemail(dto.email())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "E-mail já Cadastrado"
            );
        }
        Usuario usuario = Usuario.builder()
            .nome(dto.nome())
            .email(dto.email())
            .telefone(dto.telefone())
            .build();
        return UsuarioDTO.Response.from(repository.save(usuario));
    }

    public UsuarioDTO.Response atualizar(Long id, UsuarioDTO.Request dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Usuário não encontrado"
                ));
        
        // verifica conflito de e-mail apenas se mudou
        if (!usuario.getEmail().equals(dto.email())
                && repository.existsByemail(dto.email())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "E-mail já Cadastrado"
            );
        }

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        return UsuarioDTO.Response.from(repository.save(usuario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
        repository.deleteById(id);
    }



}
