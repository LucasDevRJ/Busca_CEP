package com.github.lucasdevrj.modelos;

import java.net.http.HttpClient;

public class ViaCepAPI {

    private HttpClient criaInstanciaDoClienteHttp() {
        HttpClient cliente = HttpClient.newHttpClient();
        return cliente;
    }
}
