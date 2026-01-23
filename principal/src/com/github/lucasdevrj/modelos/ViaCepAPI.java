package com.github.lucasdevrj.modelos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class ViaCepAPI {

    private String cep;
    private final String api = "viacep.com.br/ws"; // 22710270/json
    private final String formato = "json";
    private final String url = "%s/%s/%s".formatted(api, cep, formato);

    private HttpClient criaInstanciaDoClienteHttp() {
        HttpClient cliente = HttpClient.newHttpClient();
        return cliente;
    }

    private HttpRequest criaRequisicaoHttp() {
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return requisicao;
    }
}
