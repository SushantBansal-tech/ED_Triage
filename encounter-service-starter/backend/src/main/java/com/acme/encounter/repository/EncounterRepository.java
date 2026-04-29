package com.acme.encounter.repository;

import com.acme.encounter.entity.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Long> {
    List<Encounter> findByPatientId(Long patientId);
}
