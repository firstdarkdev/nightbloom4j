package com.hypherionmc.nightbloom.model;

import lombok.Getter;

import java.util.Date;
import java.util.List;

/**
 * @author HypherionSA
 */
@Getter
public class ProjectResponse {

    private int id;
    private String name;
    private String slug;
    private String description;
    private String logo;
    private ProjectLinks links;
    private int downloads;
    private List<FileSummary> files;
    private Date createdAt;
    private Date updatedAt;

}
