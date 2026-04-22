package com.acme.encounter.service;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.entity.EncounterStatus;

import java.util.List;

public interface EncounterService {
    List<EncounterResponse> getAll();
    EncounterResponse getById(Long id);
    EncounterResponse create(CreateEncounterRequest request);
    EncounterResponse updateStatus(Long id, EncounterStatus status);
}
