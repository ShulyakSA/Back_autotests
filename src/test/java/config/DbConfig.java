package config;

import lombok.Data;

@Data
public class DbConfig {
    private  String dbUrl;
    private  String dbUsername;
    private  String dbPassword;
}
