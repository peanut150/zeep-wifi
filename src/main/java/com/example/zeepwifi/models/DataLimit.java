package com.example.zeepwifi.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Cacheable
@Data
@Entity
@Table(name = "data_limits")
public class DataLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Integer client_id;

    @Column(name = "package_id", nullable = false)
    private Integer package_id;

    @Column(name = "limit_count", nullable = false)
    private Integer limit_count;

    @Column(name = "limit_type", nullable = false)
    private String limit_type;

    @Column(name = "counter", nullable = false)
    private Long counter;

    @Column(name = "incoming_packets")
    private Float incoming_packets;

    @Column(name = "outgoing_packets")
    private Float outgoing_packets;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime created_on;

    @UpdateTimestamp
    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;
}