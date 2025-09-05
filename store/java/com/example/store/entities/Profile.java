package com.example.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "profiles")
@Entity
public class Profile {
    //forien key
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="bio")
    private String bio;

    @Column(name="phone_number")
    private String phone_number;

    @Column(name="date_of_birth")
    private LocalDate date_of_birth;

    @Column(name="loyalty_points")
    private Integer loyalty_points;

    @OneToOne
    //this is owner as profile as a forign key rely on user
    //cascade goes to owning side in oneToOne.
    @JoinColumn(name = "id")
    //if we reference a column that is other than primary key, we need to include referencedColumnName
    //@JoinColumn(name = "id", referencedColumnName="column_name")
    //tell hibernate to use same column for both primary and foreign key of the entity
    @MapsId
    private User user;
}
