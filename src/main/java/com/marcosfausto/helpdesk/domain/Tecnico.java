package com.marcosfausto.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.marcosfausto.helpdesk.domain.dtos.TecnicoDTO;
import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
public class Tecnico extends Pessoa {

    private static final long serialVersionUID= 1L;

    public Tecnico() {
        setPerfis(Perfil.TECNICO);
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
        setPerfis(Perfil.TECNICO);
    }

    public Tecnico(TecnicoDTO tecnicoDTO) {
        this.id = tecnicoDTO.getId();
        this.nome = tecnicoDTO.getNome();
        this.cpf = tecnicoDTO.getCpf();
        this.email = tecnicoDTO.getEmail();
        this.senha = tecnicoDTO.getSenha();
        this.perfis = tecnicoDTO.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
        this.dataCriacao = tecnicoDTO.getDataCriacao();
        setPerfis(Perfil.CLIENTE);
        setPerfis(Perfil.TECNICO);
    }


}
