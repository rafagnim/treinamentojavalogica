package com.estacoes.estacoes.services;

public class ValidaCNPJ {

    public static boolean valida (String cnpj) {

        if (cnpj.length() < 14) {
            int digitosFaltantes = 14-cnpj.length();
            String acrescentar = "";
            for (int i = 0; i < digitosFaltantes; i++) {
                acrescentar += "0";
            }
            cnpj = acrescentar + cnpj;
        }

        try {

            String caracteresNumericos[] = cnpj.substring(0, 12).split("");
            int soma1 = 0, soma2 = 0;

            for (int i = 1, j = 6, k = 5; i <= 12; i++, j++, k++) {
                if (i == 5) j = 2;
                soma1 += j * Integer.parseInt(caracteresNumericos[i-1]);
                if (i == 6) k = 2;
                soma2 += k * Integer.parseInt(caracteresNumericos[i-1]);
            }

            int primeiroDigito = (soma1 % 11);
            if (primeiroDigito > 9) primeiroDigito = 0;
            soma2 += primeiroDigito * 9;
            int segundoDigito = (soma2 % 11);
            if (segundoDigito > 9) segundoDigito = 0;

            int digitos = Integer.parseInt(cnpj.substring(12));

            if (digitos == primeiroDigito * 10 + segundoDigito) {
                return true;
            }
        }catch (Exception e) {
            return false;
        }
        return false;
    }
}
