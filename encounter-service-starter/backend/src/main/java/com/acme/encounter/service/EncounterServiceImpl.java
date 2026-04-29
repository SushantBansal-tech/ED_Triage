package com.acme.encounter.service;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.entity.CurrentUserPrincipal;
import com.acme.encounter.entity.Encounter;
import com.acme.encounter.entity.EncounterStatus;
//import com.acme.encounter.exception.ResourceNotFoundException;
import com.acme.encounter.mapper.EncounterMapper;
import com.acme.encounter.repository.EncounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService {

    private final EncounterRepository encounterRepository;
    private final EncounterAuthorizationService encounterAuthorizationService;

    @Override
    public List<EncounterResponse> getAll(CurrentUserPrincipal currentUser) {
        if (!(currentUser.hasRole("ROLE_ADMIN")
                || currentUser.hasRole("ROLE_DOCTOR")
                || currentUser.hasRole("ROLE_NURSE"))) {
            throw new AccessDeniedException("Not authorized to view all encounters");
        }

        return encounterRepository.findAll()
                .stream()
                .map(EncounterMapper::toResponse)
                .toList();
    }

    @Override
    public List<EncounterResponse> getMyEncounters(CurrentUserPrincipal currentUser) {
        if (!currentUser.hasRole("ROLE_PATIENT")) {
            throw new AccessDeniedException("Only patients can access their own encounters");
        }

        if (currentUser.getPatientId() == null) {
            throw new AccessDeniedException("Patient mapping not found for current user");
        }

        return encounterRepository.findByPatientId(currentUser.getPatientId())
                .stream()
                .map(EncounterMapper::toResponse)
                .toList();
    }

    @Override
    public EncounterResponse getById(Long id, CurrentUserPrincipal currentUser) {
        Encounter encounter = encounterAuthorizationService.requireEncounterAccess(id, currentUser);
        return EncounterMapper.toResponse(encounter);
    }

    @Override
    public EncounterResponse create(CreateEncounterRequest request, CurrentUserPrincipal currentUser) {
        if (!(currentUser.hasRole("ROLE_ADMIN")
                || currentUser.hasRole("ROLE_DOCTOR")
                || currentUser.hasRole("ROLE_NURSE"))) {
            throw new AccessDeniedException("Not authorized to create encounter");
        }

        LocalDateTime now = LocalDateTime.now();
        Encounter encounter = new Encounter();
        encounter.setEncounterNumber("ENC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        encounter.setPatientId(request.getPatientId());
        encounter.setPatientName(request.getPatientName());
        encounter.setEncounterClass(request.getEncounterClass());
        encounter.setPriority(request.getPriority());
        encounter.setLocation(request.getLocation());
        encounter.setReasonForVisit(request.getReasonForVisit());
        encounter.setStatus(EncounterStatus.ARRIVED);
        encounter.setCreatedAt(now);
        encounter.setUpdatedAt(now);

        return EncounterMapper.toResponse(encounterRepository.save(encounter));
    }

    @Override
    public EncounterResponse updateStatus(Long id, EncounterStatus status, CurrentUserPrincipal currentUser) {
        Encounter encounter = encounterAuthorizationService.requireEncounterAccess(id, currentUser);

        if (!(currentUser.hasRole("ROLE_ADMIN")
                || currentUser.hasRole("ROLE_DOCTOR")
                || currentUser.hasRole("ROLE_NURSE"))) {
            throw new AccessDeniedException("Not authorized to update encounter status");
        }

        encounter.setStatus(status);
        encounter.setUpdatedAt(LocalDateTime.now());

        if (status == EncounterStatus.IN_PROGRESS && encounter.getStartedAt() == null) {
            encounter.setStartedAt(LocalDateTime.now());
        }

        if ((status == EncounterStatus.DISCHARGED || status == EncounterStatus.COMPLETED)
                && encounter.getEndedAt() == null) {
            encounter.setEndedAt(LocalDateTime.now());
        }

        return EncounterMapper.toResponse(encounterRepository.save(encounter));
    }

    // private Encounter findEncounter(Long id) {
    //     return encounterRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Encounter not found: " + id));
    // }
}