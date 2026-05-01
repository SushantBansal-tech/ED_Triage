package com.acme.encounter.repository;

import com.acme.encounter.entity.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VitalSignRepository extends JpaRepository<VitalSign, Long> {

}
