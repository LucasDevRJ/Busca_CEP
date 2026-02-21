package com.github.lucasdevrj.api;

import com.github.lucasdevrj.excecoes.ErroCepInvalidoException;
import com.github.lucasdevrj.modelos.Endereco;
import com.github.lucasdevrj.modelos.EnderecoViaCep;
import com.google.gson.Gson;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ViaCepAPI {

    private String cep;
    private final String api = "viacep.com.br/ws";
    private final String formato = "json";
    private String url;
    private static ArrayList<Endereco> listaDeEnderecos = new ArrayList<>();
    private final String nomeDoArquivo = "enderecos.txt";

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
    }

    private void escreveEnderecoNoArquivo(String jsonRetornado) throws IOException {
        FileWriter escritor = new FileWriter(nomeDoArquivo);
        escritor.write(jsonRetornado);
        escritor.close();
    }

    private String retornaJson() throws IOException, InterruptedException {
        String jsonRetornado = null;
        try {
            pesquisaCep();
            jsonRetornado = respostaDoServidor();
            escreveEnderecoNoArquivo(jsonRetornado);
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

    public void exibeEnderecosOrdenados() {
        System.out.println("|-------------------** Lista de CEPs Ordenados **-------------------|");
        Collections.sort(listaDeEnderecos);
        for (Endereco endereco : listaDeEnderecos) {
            System.out.println(endereco);
        }
        System.out.println("|-------------------*****************************---------------------|");
    }

    private int retornaQuantidadeDeCepsPesquisados() {
        return listaDeEnderecos.size();
    }

    private Map<String, Integer> retornaContadorDeEstado() {
        Map<String, Integer> contadorPorEstado = new HashMap<>();

        for (Endereco endereco : listaDeEnderecos) {
            String estado = endereco.getEstado();

            if (contadorPorEstado.containsKey(estado)) {
                contadorPorEstado.put(estado, contadorPorEstado.get(estado) + 1);
            } else {
                contadorPorEstado.put(estado, 1);
            }
        }
        return contadorPorEstado;
    }

    private String retornaEstadoMenosPesquisado() {
        Map<String, Integer> contadorPorEstado = retornaContadorDeEstado();
        String estadoMenosPesquisado = null;
        int menorQuantidade = listaDeEnderecos.size() - 1;

        for (Map.Entry<String, Integer> entry : contadorPorEstado.entrySet()) {
            if (entry.getValue() < menorQuantidade) {
                menorQuantidade = entry.getValue();
                estadoMenosPesquisado = entry.getKey();
            }
        }

        return estadoMenosPesquisado;
    }

    private String retornaEstadoMaisPesquisado() {
        Map<String, Integer> contadorPorEstado = retornaContadorDeEstado();
        String estadoMaisPesquisado = null;
        int maiorQuantidade = 0;

        for (Map.Entry<String, Integer> entry : contadorPorEstado.entrySet()) {
            if (entry.getValue() > maiorQuantidade) {
                maiorQuantidade = entry.getValue();
                estadoMaisPesquisado = entry.getKey();
            }
        }

        return estadoMaisPesquisado;
    }

    private String retornaUltimoCepPesquisado() {
        int indiceDoUltimoCepPesquisado = retornaQuantidadeDeCepsPesquisados() - 1;
        Endereco endereco = listaDeEnderecos.get(indiceDoUltimoCepPesquisado);
        String cep = endereco.getCep();
        return cep;
    }

    private String retornaPrimeiroCepPesquisado() {
        Endereco enderecoPrimeiroCepPesquisado = listaDeEnderecos.get(0);
        String primeiroCepPesquisado = enderecoPrimeiroCepPesquisado.getCep();
        return primeiroCepPesquisado;
    }

    private List<String> retornaTop3EstadosMaisPesquisados() {

        // 1) Contar quantas vezes cada estado aparece
        Map<String, Integer> contadorPorEstado = new HashMap<>();

        for (Endereco endereco : listaDeEnderecos) {
            String estado = endereco.getEstado();
            contadorPorEstado.put(estado, contadorPorEstado.getOrDefault(estado, 0) + 1);
        }

        // 2) Transformar o Map em uma lista de entradas
        List<Map.Entry<String, Integer>> listaOrdenavel =
                new ArrayList<>(contadorPorEstado.entrySet());

        // 3) Ordenar pela quantidade (do maior para o menor)
        listaOrdenavel.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // 4) Pegar os 3 primeiros (ou menos, se nao tiver 3)
        List<String> topEstados = new ArrayList<>();
        int limite = Math.min(3, listaOrdenavel.size());

        for (int i = 0; i < limite; i++) {
            String formatacao = "%dª".formatted(i+1);
            topEstados.add(formatacao + listaOrdenavel.get(i).getKey());
        }

        return topEstados;
    }

    public void exibeEstatisticas() {
        if (listaDeEnderecos.size() > 0) {
            System.out.println("|-------------------** Estatisticas **-------------------|");
            System.out.println("Quantidade de CEPs pesquisados: " + retornaQuantidadeDeCepsPesquisados());
            System.out.println("Estado mais pesquisado: " + retornaEstadoMaisPesquisado());
            System.out.println("Estado menos pesquisado: " + retornaEstadoMenosPesquisado());
            System.out.println("Top 3 Estados mais pesquisados: " + retornaTop3EstadosMaisPesquisados());
            System.out.println("Primeiro CEP pesquisado: " + retornaPrimeiroCepPesquisado());
            System.out.println("Último CEP pesquisado: " + retornaUltimoCepPesquisado());
            System.out.println("|-------------------*******************---------------------|");
        } else {
            System.out.println("Ainda não foi pesquisado nenhum CEP.");
        }
    }

    public void finalizar() {
        System.out.println("Programa finalizado.");
    }
}
