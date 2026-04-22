package com.acme.encounter.service;

import com.acme.encounter.dto.CreateEncounterRequest;
import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.entity.Encounter;
import com.acme.encounter.entity.EncounterStatus;
import com.acme.encounter.exception.ResourceNotFoundException;
import com.acme.encounter.mapper.EncounterMapper;
import com.acme.encounter.repository.EncounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;

    @Override
    public List<EncounterResponse> getAll() {
        return encounterRepository.findAll().stream().map(EncounterMapper::toResponse).toList();
    }

    @Override
    public EncounterResponse getById(Long id) {
        return EncounterMapper.toResponse(findEncounter(id));
    }

    @Override
    public EncounterResponse create(CreateEncounterRequest request) {
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
    public EncounterResponse updateStatus(Long id, EncounterStatus status) {
        Encounter encounter = findEncounter(id);
        encounter.setStatus(status);
        encounter.setUpdatedAt(LocalDateTime.now());
        if (status == EncounterStatus.IN_PROGRESS && encounter.getStartedAt() == null) {
            encounter.setStartedAt(LocalDateTime.now());
        }
        if ((status == EncounterStatus.DISCHARGED || status == EncounterStatus.COMPLETED) && encounter.getEndedAt() == null) {
            encounter.setEndedAt(LocalDateTime.now());
        }
        return EncounterMapper.toResponse(encounterRepository.save(encounter));
    }

    private Encounter findEncounter(Long id) {
        return encounterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encounter not found: " + id));
    }
}
