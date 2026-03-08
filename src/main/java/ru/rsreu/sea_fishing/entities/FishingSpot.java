package ru.rsreu.sea_fishing.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fishing_spots")
public class FishingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Integer spotId;

    @Column(name = "spot_name", length = 100)
    private String spotName;

    @Column(name = "depth_meters")
    private Integer depthMeters;

    @Column(name = "bottom_type", length = 50)
    private String bottomType;
}