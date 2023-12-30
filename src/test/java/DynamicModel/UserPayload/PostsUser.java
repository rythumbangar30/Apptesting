package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class PostsUser {
    private int id;
    private String user_id;
    private String title;
    private String body;
    public String getPostsUser_id(){
        return user_id;
    }
    public void setPostsUser_id(String user_id){
        this.user_id=user_id;
    }
    public String getPostsTitle(){
        return title;
    }
    public void setPostsTitle(String title){
        this.title=title;
    }
    public String getPostsBody(){
        return body;
    }
    public void setPostsBody(String body){
        this.body=body;
    }

}
