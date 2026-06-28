package com.challenge.cuentas.domain.model;

import java.math.BigDecimal;

public record SaldoUf(
        String numero,
        BigDecimal saldoDisponible,
        BigDecimal uf,
        BigDecimal saldoEnUf) {
}
