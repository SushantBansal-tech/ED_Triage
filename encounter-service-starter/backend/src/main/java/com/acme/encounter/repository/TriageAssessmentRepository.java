package com.acme.encounter.repository;

import com.acme.encounter.entity.TriageAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriageAssessmentRepository extends JpaRepository<TriageAssessment, String> {

    // Find all assessments associated with a specific encounter
    List<TriageAssessment> findByEncounterId(String encounterId);

    // Find a specific assessment by encounter ID and status (e.g., "OPEN")
    Optional<TriageAssessment> findByEncounterIdAndTriageStatus(String encounterId, String triageStatus);
}