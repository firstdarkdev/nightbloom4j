package com.hypherionmc.nightbloom.model;

import com.google.gson.Gson;
import lombok.Getter;

/**
 * @author HypherionSA
 */
@Getter
public class StandardResponse {

    private boolean error;
    private String message;
    private Object data;

    /**
     * Cast the data variable into a Java Class representing the data returned
     * @param clazz The class to cast to
     * @return The class to cast to
     * @param <T> The class to cast to
     */
    public <T> T getDataAs(Class<T> clazz) {
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);
        return gson.fromJson(jsonData, clazz);
    }
}
