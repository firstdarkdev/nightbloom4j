package com.hypherionmc.nightbloom.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HypherionSA
 */
@Builder
@Getter
public class ProjectMeta {

    private final String version;
    private final List<String> modloaders = new ArrayList<>();
    private final List<String> minecraftVersions = new ArrayList<>();
    private final String changelog;

    public void addModloader(String loader) {
        if (!modloaders.contains(loader))
            this.modloaders.add(loader);
    }

    public void addMinecraft(String mc) {
        if (!minecraftVersions.contains(mc))
            minecraftVersions.add(mc);
    }
}
