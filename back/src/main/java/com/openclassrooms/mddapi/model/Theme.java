package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = { "subscribers", "articles" })
@ToString(exclude = { "subscribers", "articles" })
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @ManyToMany(mappedBy = "subscribedThemes")
    private Set<User> subscribers;

    @JsonManagedReference
    @OneToMany(mappedBy = "theme")
    private Set<Article> articles;
}