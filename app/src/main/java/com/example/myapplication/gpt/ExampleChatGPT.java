//package com.example.myapplication.gpt;
//
//import io.github.jetkai.openai.api.CreateChatCompletion;
//import io.github.jetkai.openai.api.data.completion.chat.ChatCompletionData;
//import io.github.jetkai.openai.api.data.completion.chat.message.ChatCompletionMessageData;
//import io.github.jetkai.openai.net.OpenAIEndpoints;
//import io.github.jetkai.openai.openai.OpenAI;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class ExampleChatGPT {
//
//    //This is a List that will store all our conversation history - This includes our chat history and the AI's
//    private final List<ChatCompletionMessageData> messageHistory = new ArrayList<>();
//
//    public static void main(String[] args) {
//        ExampleChatGPT gpt = new ExampleChatGPT();
//
//        //The first message that we want to send
//        String message1 = "Hello ChatGPT!";
//        //The second message that we want to send
//        String message2 = "What was the first thing I said?";
//
//        //Response 1 from ChatGPT
//        String response1 = gpt.exampleBuilders(message1);
//        System.out.println("Sent: " + message1);
//        System.out.println("Response: " + response1);
//
//        //Response 2 from ChatGPT
//        String response2 = gpt.exampleBuilders(message2);
//        System.out.println("Sent: " + message2);
//        System.out.println("Response: " + response2);
//    }
//
//    private String exampleBuilders(String message) {
//        //Create the Message Data object with the message we wish to send
//        ChatCompletionMessageData messageData = ChatCompletionMessageData.builder()
//                .setRole("user")
//                .setContent(message)
//                .build();
//
//        //Store the message that we want to send, to the MessageHistory List
//        messageHistory.add(messageData);
//
//        //Build the data structure which contains the message history and model information
//        ChatCompletionData completionData = ChatCompletionData.builder()
//                .setModel("gpt-3.5-turbo")
//                .setMessages(messageHistory)
//                .build();
//
//        //Sends the request to OpenAI's endpoint & parses the response data
//        //Instead of openAI.sendRequest(); you can initialize the request
//        OpenAI openAI = OpenAI.builder()
//                .setApiKey("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")
//                .createChatCompletion(completionData)
//                .build()
//                .sendRequest();
//
//        //Check to see if there is a valid response from OpenAI
//        CreateChatCompletion createChatCompletion = openAI.getChatCompletion();
//
//        //Store chat response from AI, this allows the AI to see the full history of our chat
//        //Including both our messages and the AI's messages
//        messageHistory.addAll(createChatCompletion.asChatResponseDataList());
//
//        //Parse the response back as plain-text
//        return createChatCompletion.asText();
//    }
//
//}