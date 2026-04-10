package ru.rsreu.sea_fishing.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "fish_types")
public class FishType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fish_id")
    private Integer fishId;

    @Column(name = "fish_name", length = 50)
    private String fishName;

    @Column(name = "difficulty_level", length = 20)
    private String difficultyLevel;

    @OneToMany(mappedBy = "fish", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SpotFauna> spots = new HashSet<>();
}