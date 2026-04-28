package com.acme.encounter.service;
import com.acme.encounter.entity.CurrentUserPrincipal;
import com.acme.encounter.entity.Encounter;

import com.acme.encounter.exception.ResourceNotFoundException;

import com.acme.encounter.repository.EncounterRepository;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;



@Service
public class EncounterAuthorizationService {

    private final EncounterRepository encounterRepository;

    public EncounterAuthorizationService(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    public Encounter requireEncounterAccess(Long encounterId, CurrentUserPrincipal currentUser) {
        Encounter encounter = encounterRepository.findById(encounterId)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found: " + encounterId));

        if (currentUser.hasRole("ROLE_ADMIN") || currentUser.hasRole("ROLE_DOCTOR") || currentUser.hasRole("ROLE_NURSE")) {
            return encounter;
        }

        if (currentUser.hasRole("ROLE_PATIENT")) {
            if (currentUser.getPatientId() == null || !currentUser.getPatientId().equals(encounter.getPatientId())) {
                throw new AccessDeniedException("Not authorized to access this encounter");
            }
            return encounter;
        }

        throw new AccessDeniedException("Not authorized");
    }
}
