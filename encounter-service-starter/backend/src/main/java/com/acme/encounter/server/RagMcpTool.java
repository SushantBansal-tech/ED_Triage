package com.acme.encounter.server;

// mcp/RagMcpTool.java
@Component @Slf4j
public class RagMcpTool implements McpTool {

    private final VectorStoreClient vectorStore;  // pgvector or Qdrant
    private final EmbeddingService embedder;

    @Override
    public ToolManifest getManifest() {
        return ToolManifest.builder()
            .name("guideline_search")
            .description("Semantic search over ESI/MTS guidelines and SOPs")
            .inputSchema(Map.of(
                "query",      "string",
                "top_k",      "integer",
                "filters",    "map<string,string>"  // e.g. {category: "respiratory"}
            ))
            .authType("api_key")
            .build();
    }

    @Override
    public McpToolResult call(Map<String, Object> params) {
        String query  = (String) params.get("query");
        int topK      = (int) params.getOrDefault("top_k", 3);

        // 1. Embed the query
        float[] queryEmbedding = embedder.embed(query);

        // 2. Metadata filter (optional) — narrows search before vector similarity
        Map<String, String> filters = (Map<String, String>)
            params.getOrDefault("filters", Map.of());

        // 3. Hybrid search: vector similarity + BM25 keyword fallback
        List<GuidelineChunk> chunks = vectorStore.search(
            queryEmbedding, topK * 2, filters   // fetch 2x, then rerank
        );

        // 4. Rerank by relevance score — keeps only top_k
        List<String> snippets = chunks.stream()
            .sorted(Comparator.comparingDouble(GuidelineChunk::getScore).reversed())
            .limit(topK)
            .map(GuidelineChunk::getText)
            .toList();

        return McpToolResult.success(snippets);
    }
}
