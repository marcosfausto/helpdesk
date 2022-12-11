package com.marcosfausto.helpdesk.resourcers;

import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.services.TecnicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    private final TecnicoService tecnicoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tecnico> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tecnicoService.findById(id));
    }
}
