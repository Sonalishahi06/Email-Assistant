package com.email.writer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;
    private final String apikey;

    public EmailGeneratorService(WebClient.Builder webclientBuilder,
            @Value("${gemini.api.url}") String baseUrl,
            @Value("${gemini.api.key}") String geminiApiKey) {
        this.webClient = webclientBuilder.baseUrl(baseUrl).build();
        this.apikey = geminiApiKey;
    }

    public String generateEmailReply(EmailRequest emailRequest) {
        //Build Prompt
        String prompt=buildPrompt(emailRequest);

        //prepare raw JSON Body
        String requestBody=String.format("""
                {
                    "contents": [
                      {
                        "parts": [
                          {
                            "text": "%s"
                          }
                        ]
                      }
                    ]
                  }
                """,prompt);

        //send Request
        String response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-3-flash-preview:generateContent")
                .build())
                .header("x-goog-api-key",apikey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //Extract Response
         return extractResponseContent(response);
         
    }

    private String extractResponseContent(String response) {

        try{
            ObjectMapper mapper=new ObjectMapper();
            JsonNode root=mapper.readTree(response);
             return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt=new StringBuilder();
        prompt.append("Generate a short, professional email reply to the given email.\n" +
                "\n" +
                "Rules:\n" +
                "- Keep it under 100 words\n" +
                "- Do not give multiple options\n" +
                "- Do not give explanation\n" +
                "- Only give final email reply\n" +
                "- Format properly (Subject + Body)\n" +
                "\n" +
                "Email:");
        if(emailRequest.getTone()!=null && !emailRequest.getTone().isEmpty()){
            prompt.append("Use a").append(emailRequest.getTone()).append("tone.");
        }
        prompt.append("Original Email: \n").append(emailRequest.getEmailContent());
        return prompt.toString();
    }
}
