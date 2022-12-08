package com.marcosfausto.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Prioridade {

    BAIXA(0, "BAIXA"),
    MEDIA(1, "MEDIA"),
    ALTA(2, "ALTA");

    private final int codigo;
    private final String descricao;

    public static Prioridade toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Prioridade perfil : Prioridade.values()) {
            if (cod.equals(perfil.getCodigo()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}