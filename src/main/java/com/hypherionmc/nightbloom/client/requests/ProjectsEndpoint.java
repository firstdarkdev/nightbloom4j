package com.hypherionmc.nightbloom.client.requests;

import com.hypherionmc.nightbloom.NightBloom4J;
import com.hypherionmc.nightbloom.client.HttpClient;
import com.hypherionmc.nightbloom.model.*;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HypherionSA
 * An Endpoint Client to interact with the /projects api endpoint
 */
@RequiredArgsConstructor
public class ProjectsEndpoint {

    private final NightBloom4J bloom4J;

    /**
     * Get a list of all Project listed on Nightbloom
     * @return A List of Projects from the Platform
     * @throws IOException Thrown when an API error occurs
     */
    public List<ProjectResponse> getAll() throws IOException {
        HttpClient client = bloom4J.getClient();
        return Arrays.stream(client.get("projects", ProjectResponse[].class)).collect(Collectors.toList());
    }

    /**
     * Get a single project listed on Nightbloom
     * @param slug The project slug. For example 'craterlib'
     * @return A single project from the Platform
     * @throws IOException Thrown when an API error occurs
     */
    public ProjectResponse get(String slug) throws IOException {
        HttpClient client = bloom4J.getClient();
        return client.get("projects/" + slug, ProjectResponse.class);
    }

    /**
     * Get all files for a single project
     * @param request An instance of {@link ProjectFilesRequest}
     * @return A list of files uploaded to the project
     * @throws IOException Thrown when an API error occurs
     */
    public PaginatedFileResult getFiles(ProjectFilesRequest request) throws IOException {
        HttpClient client = bloom4J.getClient();
        return client.get(request.buildRequest(), PaginatedFileResult.class);
    }

    /**
     * Get a single file from a project
     * @param slug The project slug. For example 'craterlib'
     * @param fileId The file ID. For example, 46
     * @return The single file from the project
     * @throws IOException Thrown when an API error occurs
     */
    public ProjectFile getFile(String slug, int fileId) throws IOException {
        HttpClient client = bloom4J.getClient();
        return client.get("projects/" + slug + "/files/" + fileId, ProjectFile.class);
    }

    /**
     * Upload a file to a project. Requires an API Key
     * @param slug The project slug to add the files to
     * @param meta The file metadata
     * @param file The file
     * @return The result of the upload
     * @throws IOException Thrown when an API error occurs
     */
    public StandardResponse uploadFile(String slug, ProjectMeta meta, File file) throws IOException {
        if (!file.getName().endsWith(".jar")) {
            throw new IOException("You can only upload JAR files to projects");
        }

        HttpClient client = bloom4J.getClient();
        return client.post("projects/" + slug + "/files", meta, file);
    }
}
