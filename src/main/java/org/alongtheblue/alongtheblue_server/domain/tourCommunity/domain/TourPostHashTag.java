//package org.alongtheblue.alongtheblue_server.global.data.tourcommunity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Data
//public class TourPostHashTag {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "userTourCourse")
//    @JsonBackReference
//    private UserTourCourse tourCourseForHashTag;
//
//    private String content;
//
//}
