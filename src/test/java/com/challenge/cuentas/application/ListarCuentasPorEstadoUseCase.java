package com.challenge.cuentas.application;

import java.util.List;

import com.challenge.cuentas.domain.model.Cuenta;

public interface ListarCuentasPorEstadoUseCase {

    List<Cuenta> listar(Cuenta.Estado estado);

}