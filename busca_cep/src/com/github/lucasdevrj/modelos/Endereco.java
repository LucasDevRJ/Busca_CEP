package com.github.lucasdevrj.modelos;

public class Endereco {

    private String cep;
    private String logradouro;
    private String complemento;
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ibge;
    private String guia;
    private String ddd;
    private String siafi;

    public Endereco(EnderecoViaCep enderecoViaCep) {
        cep = enderecoViaCep.cep();
        logradouro = enderecoViaCep.logradouro();
        complemento = enderecoViaCep.complemento();
        unidade = enderecoViaCep.unidade();
        bairro = enderecoViaCep.bairro();
        localidade = enderecoViaCep.localidade();
        uf = enderecoViaCep.uf();
        estado = enderecoViaCep.estado();
        regiao = enderecoViaCep.regiao();
        ibge = enderecoViaCep.ibge();
        guia = enderecoViaCep.guia();
        ddd = enderecoViaCep.ddd();
        siafi = enderecoViaCep.siafi();
    }

    public String exibeEnderecoCompleto() {
        String endereco = """
                |-------------------** CEP Encontrado **-------------------|
                CEP: %s
                Logradouro: %s
                Complemento: %s
                Unidade: %s
                Bairro: %s
                Localidade: %s
                UF: %s
                Estado: %s
                Região: %s
                IBGE: %s
                Guia: %s
                DDD: %s
                SIAF: %s
                |-------------------********************-------------------|
                """.formatted(cep, localidade, complemento, unidade, bairro, localidade, uf, estado, regiao, ibge,
                guia, ddd, siafi);
        return endereco;
    }

    @Override
    public String toString() {
        return """
                |-------------------** CEP Encontrado **-------------------|
                Estado: %s
                UF: %s
                Região: %s
                Localidade: %s
                Bairro: %s
                Logradouro: %s
                |-------------------********************-------------------|
                """.formatted(estado, uf, regiao, localidade, bairro, logradouro);
    }
}
