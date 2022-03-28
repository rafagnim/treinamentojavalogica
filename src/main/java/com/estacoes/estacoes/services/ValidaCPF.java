package com.estacoes.estacoes.services;

public class ValidaCPF {

    public static boolean valida (String cpf) {

        if (cpf.length() < 11) {
            int digitosFaltantes = 11-cpf.length();
            String acrescentar = "";
            for (int i = 0; i < digitosFaltantes; i++) {
                acrescentar += "0";
            }
            cpf = acrescentar + cpf;
        }

        try {

            String caracteresNumericos[] = cpf.substring(0, 9).split("");
            int soma1 = 0, soma2 = 0;
            for (int i = 0, j = 10; i < 9; i++, j--) {
                soma1 += j * Integer.parseInt(caracteresNumericos[i]);
                soma2 += (j + 1) * Integer.parseInt(caracteresNumericos[i]);
            }
            int primeiroDigito = 11 - (soma1 % 11);
            if (primeiroDigito > 9) primeiroDigito = 0;
            soma2 += primeiroDigito * 2;
            int segundoDigito = 11 - (soma2 % 11);
            if (segundoDigito > 9) segundoDigito = 0;

            int digitos = Integer.parseInt(cpf.substring(9));

            if (digitos == primeiroDigito * 10 + segundoDigito) {
                return true;
            }
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
