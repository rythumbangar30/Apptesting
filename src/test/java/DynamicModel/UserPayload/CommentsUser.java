package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class CommentsUser {
private int id;
private String post_id;
private String name;
private String email;
private String body;
public String getCommentsPost_id(){
    return post_id;
}
public void setCommentsPost_id(String post_id){
    this.post_id=post_id;
}
public String getCommentsName(){
        return name;
}
public void setCommentsName(String name){
        this.name=name;
}
public String getCommentsEmail(){
        return email;
}
public void setCommentsEmail(String email){
        this.email=email;
}
public String getCommentsBody(){
        return body;
}
public void setCommentsBody(String body){
     this.body=body;
}
}
