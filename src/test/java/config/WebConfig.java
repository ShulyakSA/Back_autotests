package config;

import lombok.Data;

@Data
public class WebConfig {
    private String baseUrl;
    private String postsPath;
    private String commentPath;
    private String user;
    private String pass;
}