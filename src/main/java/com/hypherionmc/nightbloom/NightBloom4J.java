package com.hypherionmc.nightbloom;

import com.hypherionmc.nightbloom.client.HttpClient;
import com.hypherionmc.nightbloom.client.agent.UserAgent;
import com.hypherionmc.nightbloom.client.requests.ProjectsEndpoint;
import com.hypherionmc.nightbloom.client.versions.V1HttpClient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author HypherionSA
 * Main API Client Class
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NightBloom4J {

    @NonNull
    private HttpClient client;

    /**
     * Create a new V1 api Client
     * @param agent The constructed {@link UserAgent} to use for API Requests
     * @return A V1 api Client
     */
    public static NightBloom4J v1(@NotNull UserAgent agent) {
        return v1(agent, "");
    }

    /**
     * Create a new V1 api Client with uploads enabled
     * @param agent The constructed {@link UserAgent} to use for API Requests
     * @param apiKey The API key to use for uploads
     * @return A V1 api Client with uploads enabled
     */
    public static NightBloom4J v1(@NotNull UserAgent agent, String apiKey) {
        HttpClient httpClient = new V1HttpClient(agent, apiKey);
        return new NightBloom4J(httpClient);
    }

    /**
     * API Endpoints for interacting with Projects
     * @return A {@link ProjectsEndpoint} endpoint client for executing project requests
     */
    public ProjectsEndpoint projects() {
        return new ProjectsEndpoint(this);
    }
}
