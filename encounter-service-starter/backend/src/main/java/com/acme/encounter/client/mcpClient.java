package com.acme.encounter.client;

import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

// client/McpClient.java
@Component
@Slf4j
public class McpClient {

    private final RestClient http;
    private final String serverUrl;
    private String sessionId;

    public McpClient(@Value("${mcp.server.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.http = RestClient.builder()
            .baseUrl(serverUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE,
                           MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    // Called once at agent startup
    public void initialize() {
        InitializeResponse resp = http.post()
            .uri("/mcp/initialize")
            .body(InitializeRequest.builder()
                .clientInfo(ClientInfo.builder()
                    .name("cdss-severity-agent")
                    .version("1.0.0")
                    .build())
                .protocolVersion("2024-11-05")
                .build())
            .retrieve()
            .body(InitializeResponse.class);

        this.sessionId = resp.getSessionId();
        log.info("MCP session established: {}", sessionId);
    }

    // Agents call this to discover tools
    public List<ToolManifest> listTools() {
        return http.get()
            .uri("/mcp/tools/list")
            .header("X-MCP-Session-Id", sessionId)
            .retrieve()
            .body(new ParameterizedTypeReference<List<ToolManifest>>() {});
    }

    // Agents call this to invoke a tool
    public <T> T callTool(String toolName,
                           Map<String, Object> args,
                           Class<T> responseType) {
        ToolCallResponse resp = http.post()
            .uri("/mcp/tools/call")
            .header("X-MCP-Session-Id", sessionId)
            .body(ToolCallRequest.builder()
                .name(toolName)
                .arguments(args)
                .build())
            .retrieve()
            .body(ToolCallResponse.class);

        if (resp.isError()) {
            throw new McpToolException("Tool error: " +
                resp.getContent().get(0).getText());
        }

        return fromJson(resp.getContent().get(0).getText(), responseType);
    }
}