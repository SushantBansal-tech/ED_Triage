package com.acme.encounter.repository;

import com.acme.encounter.entity.TriageAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriageAssessmentRepository extends JpaRepository<TriageAssessment, Long> {

    // Find all assessments associated with a specific encounter
    List<TriageAssessment> findByEncounterId(String encounterId);

    // Find a specific assessment by encounter ID and status (e.g., "OPEN")
    Optional<TriageAssessment> findByEncounterIdAndTriageStatus(String encounterId, String triageStatus);

    // Update status method: This directly updates the DB column
    @Modifying
    @Query("UPDATE TriageAssessment t SET t.triageStatus = :status WHERE t.triageAssessmentId = :id")
    void updateTriageStatus(@Param("id") String id, @Param("status") String status);
}