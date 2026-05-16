package com.acme.encounter.server;

// server/McpServer.java
@RestController
@RequestMapping("/mcp")
@RequiredArgsConstructor
@Slf4j
public class McpServer {

    private final McpToolRegistry registry;
    private final McpSessionManager sessionManager;

    // Step 1: Client connects and negotiates capabilities
    @PostMapping("/initialize")
    public InitializeResponse initialize(@RequestBody InitializeRequest req) {
        String sessionId = sessionManager.createSession(req.getClientInfo());
        return InitializeResponse.builder()
            .sessionId(sessionId)
            .protocolVersion("2024-11-05")
            .capabilities(ServerCapabilities.builder()
                .tools(ToolsCapability.builder().listChanged(true).build())
                .build())
            .serverInfo(ServerInfo.builder()
                .name("cdss-mcp-server")
                .version("1.0.0")
                .build())
            .build();
    }

    // Step 2: Client discovers available tools
    @GetMapping("/tools/list")
    public ToolsListResponse listTools(
            @RequestHeader("X-MCP-Session-Id") String sessionId) {
        sessionManager.validate(sessionId);
        return ToolsListResponse.builder()
            .tools(registry.listTools())
            .build();
    }

    // Step 3: Client calls a tool
    @PostMapping("/tools/call")
    public ToolCallResponse callTool(
            @RequestHeader("X-MCP-Session-Id") String sessionId,
            @RequestBody ToolCallRequest req) {
        sessionManager.validate(sessionId);
        log.info("Tool call: {} by session {}", req.getName(), sessionId);
        try {
            McpToolResult result = registry.getTool(req.getName())
                                           .call(req.getArguments());
            return ToolCallResponse.builder()
                .content(List.of(TextContent.of(toJson(result.getData()))))
                .isError(false)
                .build();
        } catch (McpToolException e) {
            return ToolCallResponse.builder()
                .content(List.of(TextContent.of(e.getMessage())))
                .isError(true)
                .build();
        }
    }

    // SSE endpoint — server pushes tool-list-changed notifications
    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter events(
            @RequestHeader("X-MCP-Session-Id") String sessionId) {
        return sessionManager.createEmitter(sessionId);
    }
}
