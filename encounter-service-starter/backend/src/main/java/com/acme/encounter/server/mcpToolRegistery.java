package com.acme.encounter.server;

// mcp/McpToolRegistry.java
@Component
public class McpToolRegistry {

    private final Map<String, McpTool> tools = new LinkedHashMap<>();

    @PostConstruct
    public void register() {
        tools.put("ehr_fetch",       new EhrMcpTool());
        tools.put("guideline_search", new RagMcpTool());
        tools.put("labs_fetch",       new LabsMcpTool());
        tools.put("audit_log",        new AuditMcpTool());
    }

    // Called by agents at startup — returns the manifest
    public List<ToolManifest> listTools() {
        return tools.values().stream()
            .map(McpTool::getManifest)
            .toList();
    }

    public McpTool getTool(String name) {
        McpTool tool = tools.get(name);
        if (tool == null) throw new McpToolNotFoundException("Unknown tool: " + name);
        return tool;
    }
}

@Data @Builder
public class ToolManifest {
    private String name;
    private String description;
    private Map<String, String> inputSchema;  // field → type
    private String authType;                  // "oauth2" | "api_key"
}
