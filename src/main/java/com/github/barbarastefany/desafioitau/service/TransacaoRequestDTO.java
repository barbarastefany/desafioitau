package com.github.barbarastefany.desafioitau.service;

import jakarta.validation.constraints.Positive;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public record TransacaoRequestDTO(

        @NotNull
        Long idPagador,

        @NotNull
        Long idRecebedor,

        @NotNull
        @Positive(message = "O valor da transação deve ser maior que zero")
        BigDecimal valor
)
{}
