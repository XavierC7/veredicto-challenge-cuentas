package com.challenge.cuentas.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.challenge.cuentas.application.ports.output.CuentaRepository;
import com.challenge.cuentas.application.ports.output.IndicadoresPort;
import com.challenge.cuentas.application.service.ConsultarSaldoEnUfService;
import com.challenge.cuentas.domain.exception.CuentaNotFoundException;
import com.challenge.cuentas.domain.model.Cuenta;
import com.challenge.cuentas.domain.model.NumeroCuenta;
import com.challenge.cuentas.domain.model.SaldoUf;

class ConsultarSaldoEnUfServiceTest {
    @Test
    @DisplayName("devuelve saldo en UF cuando la cuenta existe")
    void devuelveSaldoEnUf() {
        Cuenta cuenta = new Cuenta(new NumeroCuenta("123456"), "Juan",
                new BigDecimal("2000000"), new BigDecimal("500000"),
                Cuenta.Estado.ACTIVA, LocalDate.now());

        CuentaRepository repositorio = new CuentaRepository() {
            @Override
            public Optional<Cuenta> buscarPor(NumeroCuenta numero) {
                return Optional.of(cuenta);
            }

            @Override
            public List<Cuenta> buscarPorEstado(Cuenta.Estado estado) {
                return List.of();
            }
        };

        IndicadoresPort indicadores = () -> new BigDecimal("40000");

        ConsultarSaldoEnUfService service = new ConsultarSaldoEnUfService(repositorio, indicadores);
        SaldoUf resultado = service.consultar(new NumeroCuenta("123456"));

        assertEquals("123456", resultado.numero());
        assertEquals(new BigDecimal("2500000"), resultado.saldoDisponible());
        assertEquals(new BigDecimal("40000"), resultado.uf());
    }

    @Test
    @DisplayName("lanza CuentaNotFoundException cuando la cuenta no existe")
    void lanzaNotFoundSiNoeExiste() {
        CuentaRepository repositorio = new CuentaRepository() {
            @Override
            public Optional<Cuenta> buscarPor(NumeroCuenta numero) {
                return Optional.empty();
            }

            @Override
            public List<Cuenta> buscarPorEstado(Cuenta.Estado estado) {
                return List.of();
            }
        };

        IndicadoresPort indicadores = () -> new BigDecimal("40000");

        ConsultarSaldoEnUfService service = new ConsultarSaldoEnUfService(repositorio, indicadores);

        assertThrows(CuentaNotFoundException.class,
                () -> service.consultar(new NumeroCuenta("000000")));
    }
}
