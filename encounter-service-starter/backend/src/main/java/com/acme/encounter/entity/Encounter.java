package com.acme.encounter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "encounters")
@Getter
@Setter
public class Encounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "encounter_number", nullable = false, unique = true)
    private String encounterNumber;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EncounterStatus status;

    @Column(name = "encounter_class", nullable = false)
    private String encounterClass;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private String location;

    @Column(name = "reason_for_visit", nullable = false, columnDefinition = "text")
    private String reasonForVisit;

    @Column(name = "attending_practitioner")
    private String attendingPractitioner;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
