package com.lab.api.usuario;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {


    //---Request---
    public record Request(
        @NotBlank(message = "Nome é Obrigatório")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "E-mail é Obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150)
        String email,

        @Size(max = 20)
        String telefone
    ) {}

    //---Response---
    public record Response(
        Long id,
        String nome,
        String email,
        String telefone,
        LocalDateTime criadoEm
    ) {
        public static Response from(Usuario u) {
            return new Response(u.getId(),
                                u.getNome(),
                                u.getEmail(),
                                u.getTelefone(),
                                u.getCriadoEm());
        }
    }
}
