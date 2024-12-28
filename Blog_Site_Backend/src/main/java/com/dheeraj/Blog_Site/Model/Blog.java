package com.dheeraj.Blog_Site.Model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Blog {

    @Id
    private Long blogId;
    private String blogName;
    @Lob
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private Users user;


    public Blog() {
        super();
    }

    public Blog(Long blogId, String blogName, String body, Users user) {
        super();
        this.blogId = blogId;
        this.blogName = blogName;
        this.body = body;
        this.user = user;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Blog [BlogId=" + blogId + ", blogName=" + blogName + ", body=" + body + ", user=" + user + "]";
    }



}

