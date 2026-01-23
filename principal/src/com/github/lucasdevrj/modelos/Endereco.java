package com.github.lucasdevrj.modelos;

public class Endereco {

    private String cep;
    private String logradouro;
    private String comeplemento;
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
        estado = enderecoViaCep.estado();
        uf = enderecoViaCep.uf();
        regiao = enderecoViaCep.regiao();
        localidade = enderecoViaCep.localidade();
        bairro = enderecoViaCep.bairro();
        logradouro = enderecoViaCep.logradouro();
    }
}
