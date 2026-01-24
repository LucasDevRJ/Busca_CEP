package com.github.lucasdevrj.principal;

import com.github.lucasdevrj.modelos.ViaCepAPI;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public void exibeMenuPrincipal() throws IOException, InterruptedException {
        String menu = """
                --------------------|BUSCA CEP|--------------------
                1 - Pesquisar CEP
                2 - Pesquisar CEP com Todas as Informações
                3 - Listar CEPs Pesquisados
                """;
        System.out.println(menu);

        Scanner entradaDeDados = new Scanner(System.in);

        System.out.print("Digite a opção desejada: ");
        int opcaoDesejada = entradaDeDados.nextInt();

        ViaCepAPI viaCepAPI = new ViaCepAPI();

        switch (opcaoDesejada) {
            case 1:
                viaCepAPI.exibeEndereco();
                break;
            case 2:
                viaCepAPI.exibeEnderecoCompleto();
                break;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Menu menu = new Menu();
        menu.exibeMenuPrincipal();
    }
}
