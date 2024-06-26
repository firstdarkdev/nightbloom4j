package com.hypherionmc.nightbloom.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hypherionmc.nightbloom.client.agent.UserAgent;
import com.hypherionmc.nightbloom.model.ProjectMeta;
import com.hypherionmc.nightbloom.model.StandardResponse;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

/**
 * @author HypherionSA
 * HTTP Client Wrapper with simple Post and Get Methods
 */
public abstract class HttpClient {

    // Private variables, mostly for internal use
    private String API_BASE = "https://api.nightbloom.cc";

    private final String baseUrl;
    private final UserAgent agent;
    private final String apiKey;
    private final OkHttpClient client;

    private final Gson gson = new Gson();

    /**
     * Create a new HTTP Client Instance
     * @param agent Constructed {@link UserAgent} to be added to Request Headers
     * @param version The API version to use. For example 1, 2, 3 etc
     * @param apiKey Optional API key. Required for POST methods
     */
    public HttpClient(UserAgent agent, String version, String apiKey) {
        this.agent = agent;
        this.baseUrl = API_BASE + "/v" + version;
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    /**
     * Build a new GET request
     * @param endPoint The endpoint the request will be executed on
     * @return The constructed Request
     */
    private Request buildRequest(String endPoint) {
        return new Request.Builder()
                .url(baseUrl + "/" + endPoint)
                .addHeader("User-Agent", agent.toString())
                .build();
    }

    /**
     * Build a new POST request
     * @param endpoint The endpoint the request will be executed on
     * @param body The Request Body that will be sent to the executed request
     * @return The constructed Request
     */
    private Request buildRequest(String endpoint, RequestBody body) {
        return new Request.Builder()
                .url(baseUrl + "/" + endpoint)
                .post(body)
                .header("access-key", this.apiKey)
                .build();
    }

    /**
     * Execute a GET request
     * @param endpoint The endpoint to execute the request on
     * @param returnType The Java Class (Model) that the request data will be returned as
     * @return The Java Class (Model) that the request data will be returned as
     * @param <T> The Java Class (Model) that the request data will be returned as
     * @throws IOException Thrown when an API error occurs
     */
    public <T> T get(String endpoint, Class<T> returnType) throws IOException {
        try (Response response = client.newCall(buildRequest(endpoint)).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody body = response.body();
            if (body != null) {
                StandardResponse responseType = gson.fromJson(body.charStream(), StandardResponse.class);

                if (responseType.isError()) {
                    throw new IOException(responseType.getMessage());
                }

                return responseType.getDataAs(returnType);
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    /**
     * Execute a POST request
     * @param endpoint The endpoint to execute the request on
     * @param data The JSON data that will be sent to the request
     * @param f The file to be uploaded
     * @return A {@link StandardResponse} as sent by the API
     * @throws IOException Thrown when an API error occurs
     */
    public StandardResponse post(String endpoint, ProjectMeta data, File f) throws IOException {
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            throw new IOException("apiKey not set");
        }

        JsonObject bodyObject = new JsonObject();
        bodyObject.addProperty("version", data.getVersion());
        bodyObject.addProperty("loader", String.join("|", data.getModloaders()));
        bodyObject.addProperty("minecraft", String.join("|", data.getMinecraftVersions()));
        bodyObject.addProperty("changelog", data.getChangelog());
        bodyObject.addProperty("type", data.getType());
        bodyObject.addProperty("dependsOn", data.getDependsOn());
        bodyObject.addProperty("displayName", data.getDisplayName());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("meta", null, RequestBody.create(new Gson().toJson(bodyObject), MediaType.parse("application/json")))
                .addFormDataPart("uploadfile", f.getName(), RequestBody.create(f, MediaType.parse("application/java-archive")))
                .build();

        try (Response response = client.newCall(buildRequest(endpoint, requestBody)).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            ResponseBody body = response.body();
            if (body != null) {
                StandardResponse responseType = gson.fromJson(body.charStream(), StandardResponse.class);

                if (responseType.isError()) {
                    throw new IOException(responseType.getMessage());
                }
                return responseType;
            } else {
                throw new IOException("Response body is null");
            }
        }
    }

    /**
     * Override the API url for the platform
     * @param url The new API url to use
     */
    public void setBaseUrl(String url) {
        this.API_BASE = url;
    }
}
