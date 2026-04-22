package com.acme.encounter.dto;

import com.acme.encounter.entity.EncounterStatus;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class EncounterResponse {
    Long id;
    String encounterNumber;
    String patientId;
    String patientName;
    EncounterStatus status;
    String encounterClass;
    String priority;
    String location;
    String reasonForVisit;
    String attendingPractitioner;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
    LocalDateTime createdAt;
}
