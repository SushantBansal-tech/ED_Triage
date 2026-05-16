package com.acme.encounter.server;

// mcp/AuditMcpTool.java
@Component
public class AuditMcpTool implements McpTool {

    private final AuditLogRepository repo;

    public void log(String toolName, String patientId,
                    Object params, String outcome) {
        repo.save(AuditLog.builder()
            .toolName(toolName)
            .patientId(patientId)
            .params(params.toString())
            .outcome(outcome)
            .calledAt(Instant.now())
            .build());
    }

    @Override
    public McpToolResult call(Map<String, Object> params) {
        log(
            (String) params.get("tool_name"),
            (String) params.get("patient_id"),
            params.get("summary"),
            (String) params.getOrDefault("outcome", "LOGGED")
        );
        return McpToolResult.success("logged");
    }
}