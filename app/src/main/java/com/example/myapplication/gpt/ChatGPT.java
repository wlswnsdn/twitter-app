package com.example.myapplication.gpt;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ChatGPT {
    public static void chatGPT(String text) {
        try {
            //String url = "https://api.openai.com/v1/completions";
            final URL url = new URI("https://api.openai.com/v1/chat/completions").toURL();
            String apiKey = "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";
            //HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);

            JSONObject data = new JSONObject();
            data.put("model", "gpt-3.5-turbo");
            data.put("prompt", text);
            data.put("max_tokens", 2000);
            data.put("temperature", 1.0);

            con.setDoOutput(true);
            con.getOutputStream().write(data.toString().getBytes());

            String output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();

            System.out.println(new JSONObject(output).getJSONArray("choices").getJSONObject(0).getString("text"));
        }catch(FileNotFoundException e){
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        catch(Exception e2){
            System.out.println("e.getMessage() = " + e2.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        chatGPT("Hello, how are you?");
    }
}