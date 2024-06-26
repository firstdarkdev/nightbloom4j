package com.hypherionmc.nightbloom.model;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedFileResult {
    private List<ProjectFile> files;
    public int page;
    public int totalPages;
    public int totalCount;
    public int pageSize;
}
