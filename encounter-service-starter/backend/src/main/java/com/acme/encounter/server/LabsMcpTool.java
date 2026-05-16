package com.acme.encounter.server;

// mcp/LabsMcpTool.java
@Component
public class LabsMcpTool implements McpTool {

    private final FhirClient fhirClient;

    private static final Map<String, String> CRITICAL_THRESHOLDS = Map.of(
        "Lactate",   "2.0",   // mmol/L
        "Troponin",  "0.04",  // µg/L
        "INR",       "3.5",
        "Glucose",   "3.0"    // hypoglycaemia floor mmol/L
    );

    @Override
    public McpToolResult call(Map<String, Object> params) {
        String patientId = (String) params.get("patient_id");
        @SuppressWarnings("unchecked")
        List<String> labNames = (List<String>) params.getOrDefault(
            "labs", List.of("Troponin","Lactate","INR","Glucose","WBC","Creatinine")
        );

        // GET /Observation?patient={id}&code={loinc_codes}&_sort=-date&_count=1
        List<LabResult> results = fhirClient.getLatestObservations(patientId, labNames)
            .stream()
            .map(obs -> LabResult.builder()
                .name(obs.getDisplay())
                .value(obs.getValue())
                .unit(obs.getUnit())
                .collectedAt(obs.getEffectiveDateTime())
                .status(classifyStatus(obs))
                .build())
            .toList();

        return McpToolResult.success(results);
    }

    private String classifyStatus(FhirObservation obs) {
        String threshold = CRITICAL_THRESHOLDS.get(obs.getDisplay());
        if (threshold == null) return "NORMAL";
        try {
            double val   = Double.parseDouble(obs.getValue());
            double limit = Double.parseDouble(threshold);
            return val > limit ? "CRITICAL" : "NORMAL";
        } catch (NumberFormatException e) {
            return "UNKNOWN";
        }
    }
}