package com.challenge.cuentas.infrastructure.client;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.cuentas.application.ports.output.FeriadosPort;

@Component
public class FeriadosAdapter implements FeriadosPort {

    private final RestClient restClient;

    public FeriadosAdapter(@Value("${feriados.url}") String url,
            @Value("${feriados.timeout-ms}") int timeoutMs) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(timeoutMs));
        factory.setReadTimeout(Duration.ofMillis(timeoutMs));

        this.restClient = RestClient.builder()
                .baseUrl(url)
                .requestFactory(factory)
                .build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LocalDate> obtenerFeriados() {

        try {
            Map<String, Object> respuesta = restClient.get()
                    .retrieve()
                    .body(Map.class);

            if (respuesta == null || !respuesta.containsKey("data")) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                        "Respuesta inválida del servicio de feriados");
            }

            List<Map<String, Object>> data = (List<Map<String, Object>>) respuesta.get("data");
            return data.stream()
                    .map(f -> LocalDate.parse((String) f.get("date")))
                    .toList();

        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Error al conectar con el servicio de feriados");
        }
    }

}
