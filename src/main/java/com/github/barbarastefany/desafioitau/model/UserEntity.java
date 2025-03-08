package com.github.barbarastefany.desafioitau.model;

import com.github.barbarastefany.desafioitau.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private UserType tipoUsuario;
    private BigDecimal saldo = BigDecimal.ZERO;
}
