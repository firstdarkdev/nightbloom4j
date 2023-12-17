package com.hypherionmc.nightbloom.client.agent;

import lombok.Builder;

/**
 * @author HypherionSA
 * User Agent constructor helper.
 * Based on https://github.com/masecla22/Modrinth4J/blob/master/src/main/java/masecla/modrinth4j/client/agent/UserAgent.java
 */
@Builder
public class UserAgent {

    // User Agent variables
    private String projectName;
    private String authorName;
    private String projectVersion;
    private String contact;

    /**
     * Override the toString method to return the constructed User Agent string
     * @return The User Agent String to be added as a header
     */
    @Override
    public String toString() {
        if (projectName == null && authorName == null && contact == null)
            return "Nightbloom4j / No User Agent";

        StringBuilder result = new StringBuilder();

        if (projectName != null) {
            if (authorName != null) {
                result.append(authorName).append("/").append(projectName);
            } else {
                result.append(projectName);
            }
        } else {
            if (authorName != null) {
                result.append(authorName);
            }
        }

        if (projectVersion != null) {
            result.append("/").append(projectVersion);
        }

        if (contact != null) {
            result.append(" (").append(contact).append(")");
        }

        return result.toString().trim();
    }
}
