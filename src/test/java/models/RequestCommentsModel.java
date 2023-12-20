package models;
/**
 * Модель ответа схемы comment
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCommentsModel {
    private int post;
    private String author;
    @JsonProperty("author_name")
    private String authorName;
    private String content;
    private String status;
}
