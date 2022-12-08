package com.marcosfausto.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private final int codigo;
    private final String descricao;

    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Status perfil : Status.values()) {
            if (cod.equals(perfil.getCodigo()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}