package com.marcosfausto.helpdesk.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Perfil {

    ADMIN(0, "ROLE_ADMIN"),
    CLIENTE(1, "ROLE_CLIENTE"),
    TECNICO(2, "ROLE_TECNICO");

    private final int codigo;
    private final String descricao;

    public static Perfil toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Perfil perfil : Perfil.values()) {
            if (cod.equals(perfil.getCodigo()))
                return perfil;
        }
        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}