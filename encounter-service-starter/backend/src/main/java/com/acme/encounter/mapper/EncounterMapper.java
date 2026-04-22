package com.acme.encounter.mapper;

import com.acme.encounter.dto.EncounterResponse;
import com.acme.encounter.entity.Encounter;

public final class EncounterMapper {
    private EncounterMapper() {}

    public static EncounterResponse toResponse(Encounter encounter) {
        return EncounterResponse.builder()
                .id(encounter.getId())
                .encounterNumber(encounter.getEncounterNumber())
                .patientId(encounter.getPatientId())
                .patientName(encounter.getPatientName())
                .status(encounter.getStatus())
                .encounterClass(encounter.getEncounterClass())
                .priority(encounter.getPriority())
                .location(encounter.getLocation())
                .reasonForVisit(encounter.getReasonForVisit())
                .attendingPractitioner(encounter.getAttendingPractitioner())
                .startedAt(encounter.getStartedAt())
                .endedAt(encounter.getEndedAt())
                .createdAt(encounter.getCreatedAt())
                .build();
    }
}
