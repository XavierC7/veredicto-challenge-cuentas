package com.challenge.cuentas.infrastructure.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.challenge.cuentas.application.ports.output.IndicadoresPort;

@Component
public class IndicadoresAdapter implements IndicadoresPort {

    private final RestClient restClient;

    public IndicadoresAdapter(@Value("${indicadores.uf-url}") String url,
            @Value("${indicadores.timeout-ms}") int timeoutMs) {
        this.restClient = RestClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public BigDecimal obtenerValorUf() {

        Map<String, Object> respuesta = restClient.get()
                .retrieve()
                .body(Map.class);

        if (respuesta == null || !respuesta.containsKey("serie")) {
            throw new RuntimeException("Respuesta inválida del servicio de indicadores");
        }

        List<Map<String, Object>> serie = (List<Map<String, Object>>) respuesta.get("serie");
        Number valor = (Number) serie.get(0).get("valor");
        return new BigDecimal(valor.toString());
    }
}
