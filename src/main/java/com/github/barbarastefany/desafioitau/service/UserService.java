package com.github.barbarastefany.desafioitau.service;

import com.github.barbarastefany.desafioitau.model.enums.UserType;
import com.github.barbarastefany.desafioitau.controller.exceptions.PagadorPessoaJuridicaException;
import com.github.barbarastefany.desafioitau.controller.exceptions.SaldoInsuficienteException;
import com.github.barbarastefany.desafioitau.controller.exceptions.UserNotFoundException;
import com.github.barbarastefany.desafioitau.model.UserEntity;
import com.github.barbarastefany.desafioitau.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity criarUsuario(UserEntity user) {
        return userRepository.save(user);
    }

    public BigDecimal consultarSaldo(@PathVariable Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get().getSaldo();
        } else {
            throw new UserNotFoundException("Usuário não encontrado");
        }
    }

    public void realizarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        UserEntity pagador = userRepository.findById(transacaoRequestDTO.idPagador())
                .orElseThrow(() -> new UserNotFoundException("Usuário pagador não encontrado"));

        UserEntity recebedor = userRepository.findById(transacaoRequestDTO.idRecebedor())
                .orElseThrow(() -> new UserNotFoundException("Usuário recebedor não encontrado"));

        if (pagador.getTipoUsuario() == UserType.PJ) {
            throw new PagadorPessoaJuridicaException("Usuário pagador é pessoa jurídica");
        }

        if (pagador.getSaldo().compareTo(transacaoRequestDTO.valor()) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }

        pagador.setSaldo(pagador.getSaldo().subtract(transacaoRequestDTO.valor()));
        recebedor.setSaldo(recebedor.getSaldo().add(transacaoRequestDTO.valor()));

        userRepository.save(pagador);
        userRepository.save(recebedor);
    }
}
