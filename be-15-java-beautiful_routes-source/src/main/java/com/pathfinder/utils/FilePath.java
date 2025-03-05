package com.pathfinder.utils;

public enum FilePath {
    PATHFILE("src/main/resources/routes.json"),
    USERFILE("src/main/resources/users.json");

    private String filePath;

    FilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
