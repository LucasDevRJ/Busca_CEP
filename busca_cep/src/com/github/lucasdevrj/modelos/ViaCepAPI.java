package com.github.lucasdevrj.modelos;

import com.github.lucasdevrj.excecoes.ErroCepInvalidoException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class ViaCepAPI {

    private String cep;
    private final String api = "viacep.com.br/ws";
    private final String formato = "json";
    private String url;
    private static ArrayList<Endereco> listaDeEnderecos = new ArrayList<>();

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

    }

    private String retornaJson() throws IOException, InterruptedException {
        String jsonRetornado = null;
        try {
            pesquisaCep();
            jsonRetornado = respostaDoServidor();
        } catch (ErroCepInvalidoException erro) {
            System.out.println("Erro: " + erro.getMessage());
        } catch (NullPointerException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
        return jsonRetornado;
    }

    private EnderecoViaCep serializarEndereco() throws IOException, InterruptedException {
        Gson gson = new Gson();
        EnderecoViaCep endereco = gson.fromJson(retornaJson(), EnderecoViaCep.class);
        return endereco;
    }

    public void exibeEndereco() throws IOException, InterruptedException {
        try {
            Endereco endereco = new Endereco(serializarEndereco());
            listaDeEnderecos.add(endereco);
            System.out.println(endereco.retornaInformacoesDoCep());
        } catch (NullPointerException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }

    public void exibeEnderecoCompleto() throws IOException, InterruptedException {
        try {
            Endereco endereco = new Endereco(serializarEndereco());
            listaDeEnderecos.add(endereco);
            System.out.println(endereco.retornaTodasInformacoesDoCep());
        } catch (NullPointerException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }

    public void exibeTodosEnderecosPesquisados() {
        if (listaDeEnderecos.size() > 0) {
            System.out.println("|-------------------** Lista de CEPs Encontrados **-------------------|");
            for (Endereco endereco : listaDeEnderecos) {
                System.out.println(endereco);
            }
            System.out.println("|-------------------*****************************---------------------|");
        } else {
            System.out.println("Ainda não foi pesquisado nenhum CEP.");
        }
    }
}
