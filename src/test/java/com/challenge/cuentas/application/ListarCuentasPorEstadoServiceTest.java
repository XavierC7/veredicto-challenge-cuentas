package com.challenge.cuentas.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.challenge.cuentas.application.service.ListarCuentasPorEstadoService;
import com.challenge.cuentas.domain.model.Cuenta;
import com.challenge.cuentas.domain.model.NumeroCuenta;

class ListarCuentasPorEstadoServiceTest {

    @Test
    @DisplayName("devuelve solo las cuentas con el estado solicitado")
    void devuelveCuentasPorEstado() {
        Cuenta activa = new Cuenta(new NumeroCuenta("123456"), "Juan",
                new BigDecimal("100"), BigDecimal.ZERO,
                Cuenta.Estado.ACTIVA, LocalDate.now());

        Cuenta bloqueada = new Cuenta(new NumeroCuenta("999999"), "Maria",
                BigDecimal.ZERO, BigDecimal.ZERO,
                Cuenta.Estado.BLOQUEADA, LocalDate.now());

        var repositorio = new com.challenge.cuentas.application.ports.output.CuentaRepository() {
            @Override
            public Optional<Cuenta> buscarPor(NumeroCuenta numero) {
                return Optional.empty();
            }

            @Override
            public List<Cuenta> buscarPorEstado(Cuenta.Estado estado) {
                return List.of(activa, bloqueada).stream()
                        .filter(c -> c.estado() == estado)
                        .toList();
            }
        };

        var service = new ListarCuentasPorEstadoService(repositorio);
        List<Cuenta> resultado = service.listar(Cuenta.Estado.ACTIVA);

        assertEquals(1, resultado.size());
        assertEquals("123456", resultado.get(0).numero().valor());
    }

}
