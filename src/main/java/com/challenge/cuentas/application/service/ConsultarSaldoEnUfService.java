package com.challenge.cuentas.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.challenge.cuentas.application.ports.input.ConsultarCuentaUseCase;
import com.challenge.cuentas.application.ports.input.ConsultarSaldoEnUfUseCase;
import com.challenge.cuentas.application.ports.output.CuentaRepository;
import com.challenge.cuentas.application.ports.output.IndicadoresPort;
import com.challenge.cuentas.domain.exception.CuentaNotFoundException;
import com.challenge.cuentas.domain.model.NumeroCuenta;
import com.challenge.cuentas.domain.model.SaldoUf;

@Service
public class ConsultarSaldoEnUfService implements ConsultarSaldoEnUfUseCase {

    private final CuentaRepository repositorio;
    private final IndicadoresPort indicadores;

    public ConsultarSaldoEnUfService(CuentaRepository repositorio,
            IndicadoresPort indicadores) {
        this.repositorio = repositorio;
        this.indicadores = indicadores;
    }

    @Override
    public SaldoUf consultar(NumeroCuenta numero) {
        var cuenta = repositorio.buscarPor(numero)
                .orElseThrow(() -> new CuentaNotFoundException(numero));

        BigDecimal uf = indicadores.obtenerValorUf();
        BigDecimal saldoDisponible = cuenta.saldoDisponible();
        BigDecimal saldoEnUf = saldoDisponible.divide(uf, 4, RoundingMode.HALF_UP);

        return new SaldoUf(cuenta.numero().valor(), saldoDisponible, uf, saldoEnUf);
    }
}
