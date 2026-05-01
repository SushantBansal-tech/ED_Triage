package com.acme.encounter.repository;

import com.acme.encounter.entity.TriageRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TriageRecommendationRepository extends JpaRepository<TriageRecommendation, Long> {
    List<TriageRecommendation> findByAssessmentIdOrderByGeneratedAtDesc(Long assessmentId);
}
