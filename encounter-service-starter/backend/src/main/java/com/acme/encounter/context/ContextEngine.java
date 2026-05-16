package com.acme.encounter.context;

// context/ContextEngine.java  (corrected version)
@Service
@RequiredArgsConstructor
public class ContextEngine {

    private final McpClient mcpClient;

    @PostConstruct
    public void init() {
        mcpClient.initialize();   // handshake once on startup
    }

    public PatientContext buildContext(String patientId,
                                       VitalsLabsInput vitals,
                                       String transcript) {
        // All 3 tool calls go through the MCP client — not direct bean calls
        CompletableFuture<PatientContext> ehrFuture =
            CompletableFuture.supplyAsync(() ->
                mcpClient.callTool("ehr_fetch",
                    Map.of("patient_id", patientId,
                           "fields", List.of("pmh","medications","allergies")),
                    PatientContext.class)
            );

        CompletableFuture<List<String>> guidelineFuture =
            CompletableFuture.supplyAsync(() ->
                mcpClient.callTool("guideline_search",
                    Map.of("query", transcript, "top_k", 3),
                    List.class)
            );

        CompletableFuture<List<LabResult>> labsFuture =
            CompletableFuture.supplyAsync(() ->
                mcpClient.callTool("labs_fetch",
                    Map.of("patient_id", patientId),
                    List.class)
            );

        PatientContext ehr      = ehrFuture.join();
        List<String> guidelines = guidelineFuture.join();
        List<LabResult> labs    = labsFuture.join();

        return PatientContext.builder()
            .patientId(patientId)
            .chiefComplaint(transcript)
            .vitals(vitals.toVitalsData())
            .pmh(ehr.getPmh())
            .medications(ehr.getMedications())
            .allergies(ehr.getAllergies())
            .labs(labs)
            .guidelineSnippets(guidelines)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
