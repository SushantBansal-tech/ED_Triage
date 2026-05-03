package com.acme.encounter.repository;

import com.acme.encounter.entity.TriageAssessment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface TriageAssessmentRepository extends JpaRepository<TriageAssessment, Long> {

    List<TriageAssessment> findByEncounterId(String encounterId);
    Optional<TriageAssessment> findByEncounterIdAndTriageStatus(String encounterId, String triageStatus);

    @Modifying
    @Query("UPDATE TriageAssessment t SET t.triageStatus = :status WHERE t.triageAssessmentId = :id")
        // 4. Use Long id to match JpaRepository<TriageAssessment, Long>
    void updateTriageStatus(@Param("id") Long id, @Param("status") String status);
}