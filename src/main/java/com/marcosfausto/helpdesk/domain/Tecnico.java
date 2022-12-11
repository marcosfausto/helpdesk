package com.marcosfausto.helpdesk.domain;

import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Tecnico extends Pessoa {

    public Tecnico() {
        setPerfis(Perfil.TECNICO);
    }

    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        setPerfis(Perfil.TECNICO);

    }


}
