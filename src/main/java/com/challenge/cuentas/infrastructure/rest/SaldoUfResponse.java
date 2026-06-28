package com.challenge.cuentas.infrastructure.rest;

import java.math.BigDecimal;

import com.challenge.cuentas.domain.model.SaldoUf;

public record SaldoUfResponse(
        String numero,
        BigDecimal saldoDisponible,
        BigDecimal uf,
        BigDecimal saldoEnUf) {

    public static SaldoUfResponse from(SaldoUf s) {
        return new SaldoUfResponse(s.numero(), s.saldoDisponible(), s.uf(), s.saldoEnUf());
    }
}
