package com.challenge.cuentas.application.ports.output;

import java.time.LocalDate;
import java.util.List;

public interface FeriadosPort {

    List<LocalDate> obtenerFeriados();

}
