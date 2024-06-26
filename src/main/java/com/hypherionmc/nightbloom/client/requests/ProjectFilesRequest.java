package com.hypherionmc.nightbloom.client.requests;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Setter
@RequiredArgsConstructor(staticName =  "create")
public class ProjectFilesRequest {

    private final String slug;

    @NotNull
    private String minecraft = "all";
    @NotNull
    private String loader = "all";
    @NotNull
    private String type = "all";
    private int page = 1;
    private int pageSize = 10;

    public String buildRequest() {
        String base = "projects/%s/files?mcVer=%s&loader=%s&type=%s&page=%s&pageSize=%s";
        return String.format(base, slug, minecraft, loader, type, page, pageSize);
    }

}
