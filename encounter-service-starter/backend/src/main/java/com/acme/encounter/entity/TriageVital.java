package com.acme.encounter.entity;

@Entity
@Table(name = "triage_vitals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TriageVital {
    @Id
    @Column(name = "triage_vital_id")
    private String triageVitalId;

    @Column(name = "triage_assessment_id", nullable = false)
    private String triageAssessmentId;

    @Column(name = "vital_type", nullable = false)
    private String vitalType;

    @Column(name = "vital_value", nullable = false)
    private Double vitalValue;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "recorded_at", nullable = false)
    private OffsetDateTime recordedAt;
}
