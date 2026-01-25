package com.github.lucasdevrj.modelos;

public class Endereco implements Comparable<Endereco> {

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
        try {
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
        } catch (NullPointerException erro) {
            throw new NullPointerException("Não foi possível instanciar o Endereço, pois ele é nulo ou inexistente.");
        }
    }

    public String getEstado() {
        return estado;
    }

    public String getCep() {
        return cep;
    }

    public String retornaInformacoesDoCep() {
        return """
                |-------------------** CEP Encontrado **-------------------|
                %s
                |-------------------********************-------------------|
                """.formatted(toString());
    }

    public String retornaTodasInformacoesDoCep() {
        String endereco = """
                |-------------------** CEP Encontrado **-------------------|
                CEP: %s
                Complemento: %s
                Unidade: %s
                IBGE: %s
                Guia: %s
                DDD: %s
                SIAF: %s
                %s
                |-------------------********************-------------------|
                """.formatted(cep, complemento, unidade, ibge, guia, ddd, siafi, toString());
        return endereco;
    }

    @Override
    public String toString() {
        return """
                Estado: %s
                UF: %s
                Região: %s
                Localidade: %s
                Bairro: %s
                Logradouro: %s
                """.formatted(estado, uf, regiao, localidade, bairro, logradouro);
    }

    @Override
    public int compareTo(Endereco outroEstado) {
        return estado.compareTo(outroEstado.estado);
    }
}
