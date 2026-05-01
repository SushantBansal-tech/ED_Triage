package com.acme.encounter.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "symptom_notes")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SymptomNote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;
    private Long assessmentId;
    private String chiefComplaint;
    private String symptomText;
    private Integer painScore;
}