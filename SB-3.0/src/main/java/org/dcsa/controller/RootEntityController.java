package org.dcsa.controller;

import lombok.RequiredArgsConstructor;
import org.dcsa.persistence.RootEntity;
import org.dcsa.service.RootEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RootEntityController {

    private final RootEntityService rootEntityService;

    @GetMapping(path = "/service-schedules")
    @ResponseStatus(HttpStatus.OK)
    public Page<RootEntity> findAll(
            @RequestParam(required = false) String withSubEntityName,
            @RequestParam(required = false, defaultValue = "100") Integer limit
    ) {
        return rootEntityService.findAll(withSubEntityName, PageRequest.of(0, limit));
    }
}
