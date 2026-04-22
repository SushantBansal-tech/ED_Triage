package com.acme.encounter.controller;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.dto.UpdateEncounterStatusRequest;
import com.acme.encounter.service.EncounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EncounterController {
    private final EncounterService encounterService;

    @GetMapping
    public List<EncounterResponse> getAll() {
        return encounterService.getAll();
    }

    @GetMapping("/{id}")
    public EncounterResponse getById(@PathVariable Long id) {
        return encounterService.getById(id);
    }

    @PostMapping
    public EncounterResponse create(@Valid @RequestBody CreateEncounterRequest request) {
        return encounterService.create(request);
    }

    @PatchMapping("/{id}/status")
    public EncounterResponse updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateEncounterStatusRequest request) {
        return encounterService.updateStatus(id, request.getStatus());
    }
}
