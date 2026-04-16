package com.example.rag.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean                        // ← Spring Boot 4.x replacement for @MockBean
    private ChatClient chatClient;

    private ChatClient.ChatClientRequestSpec promptSpec;
    private ChatClient.CallResponseSpec callSpec;

    @BeforeEach
    void setup() {
        promptSpec = mock(ChatClient.ChatClientRequestSpec.class);
        callSpec   = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(promptSpec);
        when(promptSpec.user(anyString())).thenReturn(promptSpec);
        when(promptSpec.advisors(any(java.util.function.Consumer.class))).thenReturn(promptSpec);
        when(promptSpec.call()).thenReturn(callSpec);
        when(callSpec.content()).thenReturn("RAG stands for Retrieval-Augmented Generation.");
    }

    @Test
    void shouldReturnAIResponseForValidMessage() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.TEXT_PLAIN)
                .content("What is RAG?"))
            .andExpect(status().isOk())
            .andExpect(content().string("RAG stands for Retrieval-Augmented Generation."));
    }

    @Test
    void shouldCallChatClientPrompt() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Explain vector search"))
            .andExpect(status().isOk());

        verify(chatClient, times(1)).prompt();
    }

    @Test
    void shouldPassUserMessageToPrompt() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.TEXT_PLAIN)
                .content("How does pgvector work?"))
            .andExpect(status().isOk());

        verify(promptSpec).user("How does pgvector work?");
    }
}