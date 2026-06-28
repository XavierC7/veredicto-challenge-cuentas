package com.challenge.cuentas.infrastructure.rest;

import java.time.LocalDate;

public record ProximoDiaHabilResponse(String fecha) {

    public static ProximoDiaHabilResponse from(LocalDate dia) {
        return new ProximoDiaHabilResponse(dia.toString());
    }

}
