package com.acme.encounter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "vital_signs")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class VitalSign {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vitalId;
    private Long assessmentId;
    private String vitalType; // spo2, heartrate, systolicbp
    private Double vitalValue;
    private String unit;
    private LocalDateTime measuredAt;
}