package com.example.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatClient chatClient;

    public ChatController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.chatClient = ChatClient.builder(ollamaChatModel)
        .defaultAdvisors(
            QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                    .topK(6)
                    .similarityThreshold(0.5)
                    .build())
                .build()
        )
        .build();
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        // Log retrieved documents to verify RAG is working
        return chatClient.prompt()
                .user(message)
                .advisors(spec -> spec.param(
                    QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS, true))
                .call()
                .content();
    }
}
