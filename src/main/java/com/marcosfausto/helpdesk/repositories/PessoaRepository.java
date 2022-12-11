package com.marcosfausto.helpdesk.repositories;

import com.marcosfausto.helpdesk.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa,Integer> {
}
