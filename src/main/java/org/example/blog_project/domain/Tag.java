//package org.example.blog_project.domain;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//@Table(name = "tags")
//public class Tag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String name;
//
//    @ManyToMany(mappedBy = "tags")
//    private List<Post> posts;
//}
