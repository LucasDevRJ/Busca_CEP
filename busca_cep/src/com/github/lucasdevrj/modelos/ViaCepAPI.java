package com.github.lucasdevrj.modelos;

import com.github.lucasdevrj.excecoes.ErroCepInvalidoException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ViaCepAPI {

    private String cep;
    private final String api = "viacep.com.br/ws"; // 22710270/json
    private final String formato = "json";
    private String url;

    private HttpClient criaInstanciaDoClienteHttp() {
        HttpClient cliente = HttpClient.newHttpClient();
        return cliente;
    }

    private HttpRequest criaRequisicaoHttp() {
        HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return requisicao;
    }

    private String respostaDoServidor() throws IOException, InterruptedException {
        HttpClient cliente = criaInstanciaDoClienteHttp();
        HttpRequest requisicao = criaRequisicaoHttp();
        HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
        String corpoDaResposta = resposta.body();
        return corpoDaResposta;
    }

    private void pesquisaCep() {
        Scanner entradaDeDados = new Scanner(System.in);

        System.out.print("Digite o CEP desejado: ");
        cep = entradaDeDados.nextLine();

        if (cep.length() != 8) {
            throw new ErroCepInvalidoException("CEP inválido! Digite somente 8 números. Exemplo: \"01001000\"");
        } else if (cep.matches(".*\\D.*")) {
            throw new ErroCepInvalidoException("CEP inválido! Digite somente números. Exemplo: \"01001000\"");
        }

        url = "https://%s/%s/%s".formatted(api, cep, formato);

        System.out.println(url);

        entradaDeDados.close();
    }

    public void consumirApi() throws IOException, InterruptedException {
        pesquisaCep();
        String jsonRetornado = respostaDoServidor();

        Gson gson = new Gson();
        EnderecoViaCep enderecoViaCep = gson.fromJson(jsonRetornado, EnderecoViaCep.class);
        Endereco endereco = new Endereco(enderecoViaCep);

        System.out.println(endereco);
    }
}
