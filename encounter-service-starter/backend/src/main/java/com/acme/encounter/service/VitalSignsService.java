package com.acme.encounter.service;

import com.acme.encounter.entity.VitalSign;
import com.acme.encounter.repository.VitalSignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class VitalSignsService {
    private final VitalSignRepository repo;
    public void capture(VitalSign vital) { repo.save(vital); }
}''',
        "SymptomNotesService.java": '''package com.acme.triage.service;
        import com.acme.triage.entity.SymptomNote;
import com.acme.triage.repository.SymptomNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class SymptomNotesService {
    private final SymptomNoteRepository repo;
    public void capture(SymptomNote note) { repo.save(note); }
}