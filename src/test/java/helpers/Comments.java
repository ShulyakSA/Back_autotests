package helpers;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comments {
    private int commentId;
    private String commentAuthor;
    private String commentContent;
    private String commentApproved;
}
