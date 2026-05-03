package com.acme.encounter.service;

import org.springframework.stereotype.Service;

@Service
public class AlertService {
    public void evaluateEscalation(Long assessmentId, String priority) {
        if ("P1".equals(priority)) {
            System.out.println("ALERT: High risk case! Triggering escalations for assessment: " + assessmentId);
        }
    }
}