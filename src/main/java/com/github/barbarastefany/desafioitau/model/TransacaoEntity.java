package com.github.barbarastefany.desafioitau.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoEntity {

    private Long id;
    private BigDecimal valor;
    private Long pagadorId;
    private Long recebedorId;
    private OffsetDateTime dataHora;
}
