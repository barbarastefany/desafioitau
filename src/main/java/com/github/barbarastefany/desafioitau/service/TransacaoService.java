package com.github.barbarastefany.desafioitau.service;

import com.github.barbarastefany.desafioitau.model.enums.UserType;
import com.github.barbarastefany.desafioitau.controller.exceptions.PagadorPessoaJuridicaException;
import com.github.barbarastefany.desafioitau.controller.exceptions.SaldoInsuficienteException;
import com.github.barbarastefany.desafioitau.controller.exceptions.UserNotFoundException;
import com.github.barbarastefany.desafioitau.model.TransacaoEntity;
import com.github.barbarastefany.desafioitau.model.UserEntity;
import com.github.barbarastefany.desafioitau.repository.TransacaoRepository;
import com.github.barbarastefany.desafioitau.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UserRepository userRepository;

    public TransacaoEntity realizarTransacao(TransacaoRequestDTO request) {
        UserEntity pagador = userRepository.findById(request.idPagador())
                .orElseThrow(() -> new UserNotFoundException("Usuário pagador não encontrado"));

        UserEntity recebedor = userRepository.findById(request.idRecebedor())
                .orElseThrow(() -> new UserNotFoundException("Usuário recebedor não encontrado"));

        if (pagador.getTipoUsuario() == UserType.PJ) {
            throw new PagadorPessoaJuridicaException("Usuário pagador é uma pessoa jurídica e não pode realizar transações");

    }

        if (pagador.getSaldo().compareTo(request.valor()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transação");
    }

        pagador.setSaldo(pagador.getSaldo().subtract(request.valor()));
        recebedor.setSaldo(recebedor.getSaldo().add(request.valor()));

        TransacaoEntity transacao = new TransacaoEntity();
        transacao.setValor(request.valor());
        transacao.setPagadorId(pagador.getId());
        transacao.setRecebedorId(recebedor.getId());
        transacao.setDataHora(OffsetDateTime.now());

        return transacaoRepository.save(transacao);
    }
}
