package com.github.barbarastefany.desafioitau.repository;

import com.github.barbarastefany.desafioitau.model.TransacaoEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TransacaoRepository {

    private final List<TransacaoEntity> transacoes = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TransacaoEntity save(TransacaoEntity transacao) {
        transacao.setId(idGenerator.getAndIncrement());
        transacao.setDataHora(OffsetDateTime.now());
        transacoes.add(transacao);
        return transacao;
    }
}
