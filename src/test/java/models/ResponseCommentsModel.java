package models;
/**
 * Модель ответа схемы comments
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
public class ResponseCommentsModel {
    private int id;
    private int post;
    private String author;
    @JsonProperty("author_name")
    private String authorName;
    private ObjectModel content;
    private String status;
}
