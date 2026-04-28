package com.acme.encounter.controller;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.dto.UpdateEncounterStatusRequest;
import com.acme.encounter.entity.CurrentUserPrincipal;
import com.acme.encounter.service.EncounterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class EncounterController {

    private final EncounterService encounterService;

    @GetMapping("/{id}")
    public ResponseEntity<EncounterResponse> getEncounter(
            @PathVariable Long id,
            @AuthenticationPrincipal CurrentUserPrincipal currentUser) {
        return ResponseEntity.ok(encounterService.getById(id, currentUser));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<EncounterResponse>> getMyEncounters(
            @AuthenticationPrincipal CurrentUserPrincipal currentUser) {
        return ResponseEntity.ok(encounterService.getAll(currentUser));
    }

    @PostMapping
    public ResponseEntity<EncounterResponse> create(
            @Valid @RequestBody CreateEncounterRequest request,
            @AuthenticationPrincipal CurrentUserPrincipal currentUser) {
        return ResponseEntity.ok(encounterService.create(request, currentUser));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EncounterResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEncounterStatusRequest request,
            @AuthenticationPrincipal CurrentUserPrincipal currentUser) {
        return ResponseEntity.ok(
                encounterService.updateStatus(id, request.getStatus(), currentUser)
        );
    }
}