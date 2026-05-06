package com.oficina.util;

import com.oficina.exception.BusinessException;

import java.util.regex.Pattern;

public class DescricaoValidator {

    private static final Pattern TELEFONE = Pattern.compile(
            "(\\(?\\d{2}\\)?[\\s-]?)?(\\d{4,5}-?\\d{4})");

    private static final Pattern CPF = Pattern.compile(
            "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}");

    private static final Pattern SEQUENCIA_NUMERICA = Pattern.compile(
            "\\d{8,}");

    public static void validar(String descricao) {
        if (descricao == null)
            return;

        if (TELEFONE.matcher(descricao).find()) {
            throw new BusinessException("Descrição não pode conter telefone ou CPF");
        }

        if (CPF.matcher(descricao).find()) {
            throw new BusinessException("Descrição não pode conter telefone ou CPF");
        }

        if (SEQUENCIA_NUMERICA.matcher(descricao).find()) {
            throw new BusinessException("Descrição não pode conter sequências numéricas longas");
        }
    }
}