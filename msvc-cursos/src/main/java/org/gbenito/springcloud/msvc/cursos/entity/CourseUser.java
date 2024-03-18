package org.gbenito.springcloud.msvc.cursos.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "course_user")
public class CourseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    public CourseUser() {
    }

    public CourseUser(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if(!(obj instanceof CourseUser)){
            return false;
        }

        CourseUser o = (CourseUser) obj;
        return this.userId != null && this.userId.equals(o.userId);
    }
}
