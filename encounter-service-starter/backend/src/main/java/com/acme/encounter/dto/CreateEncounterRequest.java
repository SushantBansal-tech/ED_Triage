package com.acme.encounter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateEncounterRequest {
    @NotBlank
    private String patientId;
    @NotBlank
    private String patientName;
    @NotBlank
    private String encounterClass;
    @NotBlank
    private String priority;
    @NotBlank
    private String location;
    @NotBlank
    private String reasonForVisit;
}
