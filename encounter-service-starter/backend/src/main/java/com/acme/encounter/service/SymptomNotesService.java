package com.acme.encounter.service;

import com.acme.encounter.entity.SymptomNote;
import com.acme.encounter.repository.SymptomNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class SymptomNotesService {
    private final SymptomNoteRepository repo;
    public void capture(SymptomNote note) { repo.save(note); }
}