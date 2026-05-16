package com.acme.encounter.server;

// mcp/EhrMcpTool.java
@Component @Slf4j
public class EhrMcpTool implements McpTool {

    private final FhirClient fhirClient;
    private final AuditMcpTool auditTool;

    @Override
    public ToolManifest getManifest() {
        return ToolManifest.builder()
            .name("ehr_fetch")
            .description("Fetch structured patient record from EHR")
            .inputSchema(Map.of(
                "patient_id", "string",
                "fields",     "array<string>"  // pmh, medications, allergies, last_visit
            ))
            .authType("oauth2")
            .build();
    }

    @Override
    public McpToolResult call(Map<String, Object> params) {
        String patientId = (String) params.get("patient_id");
        @SuppressWarnings("unchecked")
        List<String> fields = (List<String>) params.get("fields");

        validateParams(patientId, fields);

        try {
            // FHIR R4 calls — each maps to a resource type
            PatientContext.PatientContextBuilder ctx = PatientContext.builder()
                .patientId(patientId)
                .timestamp(LocalDateTime.now());

            if (fields.contains("pmh")) {
                List<String> conditions = fhirClient
                    .getConditions(patientId)          // GET /Condition?patient={id}
                    .stream()
                    .map(FhirCondition::getDisplay)
                    .toList();
                ctx.pmh(conditions);
            }

            if (fields.contains("medications")) {
                List<String> meds = fhirClient
                    .getMedications(patientId)         // GET /MedicationRequest?patient={id}
                    .stream()
                    .map(m -> m.getName() + " " + m.getDose())
                    .toList();
                ctx.medications(meds);
            }

            if (fields.contains("allergies")) {
                ctx.allergies(fhirClient.getAllergies(patientId));
            }

            PatientContext result = ctx.build();
            auditTool.log("ehr_fetch", patientId, fields, "SUCCESS");
            return McpToolResult.success(result);

        } catch (FhirException e) {
            auditTool.log("ehr_fetch", patientId, fields, "ERROR: " + e.getMessage());
            throw new McpToolException("EHR fetch failed for patient " + patientId, e);
        }
    }

    private void validateParams(String patientId, List<String> fields) {
        if (patientId == null || patientId.isBlank())
            throw new McpValidationException("patient_id is required");
        if (fields == null || fields.isEmpty())
            throw new McpValidationException("fields[] must not be empty");
    }
}
