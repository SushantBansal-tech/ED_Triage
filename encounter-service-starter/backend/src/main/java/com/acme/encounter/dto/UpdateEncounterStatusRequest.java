package com.acme.encounter.dto;

import com.acme.encounter.entity.EncounterStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateEncounterStatusRequest {
    @NotNull
    private EncounterStatus status;
}
