package com.acme.encounter.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

// rest of class unchanged

@Entity
@Table(name = "triage_assessments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TriageAssessment {
    @Id
    @Column(name = "triage_assessment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long triageAssessmentId;

    @Column(name = "encounter_id", nullable = false)
    private String encounterId;

    @Column(name = "triage_status", nullable = false)
    private String triageStatus;

    @Column(name = "chief_complaint_detail")
    private String chiefComplaintDetail;

    @Column(name = "triage_note")
    private String triageNote;

    @Column(name = "pain_score")
    private Integer painScore;

    @Column(name = "assessed_by", nullable = false)
    private String assessedBy;

    @Column(name = "assessment_started_at", nullable = false)
    private OffsetDateTime assessmentStartedAt;

    @Column(name = "assessment_completed_at")
    private OffsetDateTime assessmentCompletedAt;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}