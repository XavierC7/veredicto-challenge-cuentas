package com.challenge.cuentas.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.cuentas.application.ports.input.ConsultarProximoDiaHabilUseCase;

@RestController
@RequestMapping("/api/v1/feriados")

public class FeriadosController {

    private final ConsultarProximoDiaHabilUseCase consultarProximoDiaHabil;

    public FeriadosController(ConsultarProximoDiaHabilUseCase consultarProximoDiaHabil) {
        this.consultarProximoDiaHabil = consultarProximoDiaHabil;
    }

    @GetMapping("/proximo")
    public ResponseEntity<ProximoDiaHabilResponse> proximoDiaHabil() {
        return ResponseEntity.ok(
                ProximoDiaHabilResponse.from(consultarProximoDiaHabil.consultar()));
    }

}
