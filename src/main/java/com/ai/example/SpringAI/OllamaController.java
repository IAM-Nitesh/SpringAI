package com.ai.example.SpringAI;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController()
@RequestMapping("/ai")
public class OllamaController {

    private OllamaChatModel chatModel;
    private final String PROMPT = "How are you ?";

    @Autowired
    public OllamaController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/prompt")
    public String getPromptResponse(@RequestParam("message") String message){
        return chatModel.call(message);
    }


    @GetMapping("/chat")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/chatStream")
    public Flux<String> generateStream(@RequestParam("message")String message) {
        chatModel.call(message);
        return chatModel.stream(message);
    }

}