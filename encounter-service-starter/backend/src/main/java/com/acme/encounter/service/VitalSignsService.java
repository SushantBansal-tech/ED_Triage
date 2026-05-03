package com.acme.encounter.service;

import com.acme.encounter.entity.VitalSign;
import com.acme.encounter.repository.VitalSignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class VitalSignsService {
    private final VitalSignRepository repo;
    public void capture(VitalSign vital) { repo.save(vital); }
}