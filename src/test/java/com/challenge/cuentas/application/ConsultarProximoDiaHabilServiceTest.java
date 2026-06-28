package com.challenge.cuentas.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.challenge.cuentas.application.ports.output.FeriadosPort;
import com.challenge.cuentas.application.service.ConsultarProximoDiaHabilService;

class ConsultarProximoDiaHabilServiceTest {

    @Test
    @DisplayName("salta fin de semana y devuelve el lunes")
    void saltaFinDeSemana() {
        FeriadosPort feriadosPort = List::of;

        ConsultarProximoDiaHabilService service = new ConsultarProximoDiaHabilService(feriadosPort);
        LocalDate resultado = service.consultar();

        assertNotNull(resultado);
        assertTrue(resultado.isAfter(LocalDate.now()));
    }

    @Test
    @DisplayName("salta feriados y devuelve el siguiente día hábil")
    void saltaFeriados() {
        LocalDate manana = LocalDate.now().plusDays(1);
        FeriadosPort feriadosPort = () -> List.of(manana);

        ConsultarProximoDiaHabilService service = new ConsultarProximoDiaHabilService(feriadosPort);
        LocalDate resultado = service.consultar();

        assertNotNull(resultado);
        assertTrue(resultado.isAfter(manana));
    }

}
