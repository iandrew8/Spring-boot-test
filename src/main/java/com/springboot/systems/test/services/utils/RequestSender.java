package com.springboot.systems.test.services.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * This class will have the various methods that can be used to send requests to various API
 */
public class RequestSender {

    private RequestSender() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();

    /**
     * This method will send a GET request to the specified URL. This will use an "Authorization" header.
     * @param url, the URL to send the request to
     * @param body, the body of the request
     * @param key, the key to use for authorization
     * @return the response from the server
     * @throws IOException, if an I/O error occurs
     * @throws InterruptedException, if the operation is interrupted
     */
    public static HttpResponse<String> sendPostRequest(String url, String body, String key) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", key)
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * This method will send a POST request to the specified URL. This will use an "X-Token" header.
     * @param url, the URL to send the request to
     * @param body, the body of the request
     * @param key, the key to use for authorization
     * @return the response from the server
     * @throws IOException, if an I/O error occurs
     * @throws InterruptedException, if the operation is interrupted
     */
    public static HttpResponse<String> sendSmartOLTPostRequest(String url, String body, String key) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("X-Token", key)
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * This method assists in converting a Map of form data to a String
     * @param formData, the Map of form data
     * @return the String representation of the form data
     */
    public static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }

    /**
     * This method will send a PUT request to the specified URL. This will use an "Authorization" header.
     * @param url, the URL to send the request to
     * @param body, the body of the request
     * @param key, the key to use for authorization
     * @return the response from the server
     * @throws IOException, if an I/O error occurs
     * @throws InterruptedException, if the operation is interrupted
     */
    public static HttpResponse<String> sendPutRequest(String url, String body, String key) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", key)
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * This method will send a GET request to the specified URL. This will use an "Authorization" header.
     * @param url, the URL to send the request to
     * @param id, the id to use for the request
     * @param key, the key to use for authorization
     * @return the response from the server
     * @throws IOException, if an I/O error occurs
     * @throws InterruptedException, if the operation is interrupted
     */
    public static HttpResponse<String> sendGetRequest(String url, String id, String key) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url + id))
                .header("Content-Type", "application/json")
                .header("Authorization", key)
                .build();

        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
