package com.acme.encounter.service;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.entity.CurrentUserPrincipal;
import com.acme.encounter.entity.EncounterStatus;

import java.util.List;

public interface EncounterService {
    List<EncounterResponse> getAll(CurrentUserPrincipal currentUser);
    List<EncounterResponse> getMyEncounters(CurrentUserPrincipal currentUser);
    EncounterResponse getById(Long id, CurrentUserPrincipal currentUser);
    EncounterResponse create(CreateEncounterRequest request, CurrentUserPrincipal currentUser);
    EncounterResponse updateStatus(Long id, EncounterStatus status, CurrentUserPrincipal currentUser);
}