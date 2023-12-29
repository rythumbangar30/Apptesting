package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class CommentsUser {
private int id;
private String post_id;
private String name;
private String email;
private String body;
}
