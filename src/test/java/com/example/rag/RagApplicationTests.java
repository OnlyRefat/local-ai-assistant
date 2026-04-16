package com.example.rag;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class RagApplicationTests {

    @MockitoBean
    OllamaChatModel ollamaChatModel;

    @MockitoBean
    VectorStore vectorStore;

    @MockitoBean
    ChatClient chatClient;

    @Test
    void contextLoads() {
    }
}