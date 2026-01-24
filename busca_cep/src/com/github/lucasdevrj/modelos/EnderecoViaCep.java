package com.github.lucasdevrj.modelos;

public record EnderecoViaCep(String cep, String logradouro, String complemento, String unidade, String bairro,
                             String localidade, String uf, String estado, String regiao, String ibge, String guia,
                             String ddd, String siafi) {
}
