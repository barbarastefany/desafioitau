package com.github.barbarastefany.desafioitau.controller;

import com.github.barbarastefany.desafioitau.model.UserEntity;
import com.github.barbarastefany.desafioitau.service.TransacaoRequestDTO;
import com.github.barbarastefany.desafioitau.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<UserEntity> criarUsuario(@RequestBody UserEntity user) {
        UserEntity novoUsuario = userService.criarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/transacao")
    public ResponseEntity<Void> realizarTransacao(@RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        userService.realizarTransacao(transacaoRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    
    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        BigDecimal saldo = userService.consultarSaldo(id);
        return ResponseEntity.ok(saldo);
    }
}
