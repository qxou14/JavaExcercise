package com.example.school_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "major")
@Data
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String majorName;

    @OneToMany(mappedBy = "major")
    private List<Student> students;

    public int getTotalStudent(){
        return students.size();
    }
}
