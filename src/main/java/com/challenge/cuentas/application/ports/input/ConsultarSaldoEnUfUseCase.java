package com.challenge.cuentas.application.ports.input;

import com.challenge.cuentas.domain.model.NumeroCuenta;
import com.challenge.cuentas.domain.model.SaldoUf;

public interface ConsultarSaldoEnUfUseCase {
    SaldoUf consultar(NumeroCuenta numero);
}
