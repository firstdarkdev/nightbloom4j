package com.hypherionmc.nightbloom.model;

import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author HypherionSA
 */
@Getter
public class FileSummary {

    private int id;
    private List<String> modLoaders;
    private List<String> minecraftVersions;
    private int downloads;
    private String version;
    private int filesize;
    private String filename;
    private String type;
    private Depends dependsOn;
    private String downloadUrl;
    private String displayName;
    private Date createdAt;
    private Date updatedAt;

}
