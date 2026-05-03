package com.acme.encounter.service;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    public void logEvent(Long assessmentId, String eventCode) {
        System.out.println("AUDIT: Assessment " + assessmentId + " event: " + eventCode);
    }
}