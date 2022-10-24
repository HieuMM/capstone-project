package com.example.fastsoccer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer token;

    private String role;
    private Long idOwn;// id chủ sân
    @Column(name = "fullName",nullable = true)
    private String fullName;
    @Column(name = "image",nullable = true)
    private String image;
    @ManyToOne
    @JoinColumn(name="district_id", nullable=true)//huyện
    private District district;
    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Booking> bookingList;
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<Post> postList;
}
