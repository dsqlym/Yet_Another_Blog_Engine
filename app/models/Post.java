package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity(name="post_info")
public class Post extends Model {

    public String title;
    public Date postedAt;

    //长文本存储
    @Lob
    public String content;

    //post与 user 是多对一
//    @ManyToOne
    public User author;

//    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
 //   public List<Comment> comments;

    public Post(User author, String title, String content) {
  //      this.comments = new ArrayList<Comment>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }

    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content).save();
   //     this.comments.add(newComment);
        this.save();
        return this;
    }

}