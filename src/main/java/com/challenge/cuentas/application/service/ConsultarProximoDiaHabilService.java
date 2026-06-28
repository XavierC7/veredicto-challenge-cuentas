package com.challenge.cuentas.application.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.cuentas.application.ports.input.ConsultarProximoDiaHabilUseCase;
import com.challenge.cuentas.application.ports.output.FeriadosPort;

@Service
public class ConsultarProximoDiaHabilService implements ConsultarProximoDiaHabilUseCase {

    private final FeriadosPort feriadosPort;

    public ConsultarProximoDiaHabilService(FeriadosPort feriadosPort) {
        this.feriadosPort = feriadosPort;
    }

    @Override
    public LocalDate consultar() {
        List<LocalDate> feriados = feriadosPort.obtenerFeriados();
        LocalDate dia = LocalDate.now().plusDays(1);

        while (esFinDeSemana(dia) || feriados.contains(dia)) {
            dia = dia.plusDays(1);
        }

        return dia;
    }

    private boolean esFinDeSemana(LocalDate dia) {
        return dia.getDayOfWeek() == DayOfWeek.SATURDAY
                || dia.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

}
