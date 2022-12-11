package com.marcosfausto.helpdesk.repositories;

import com.marcosfausto.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico,Integer> {
}
