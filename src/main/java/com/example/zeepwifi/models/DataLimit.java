package com.example.zeepwifi.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Cacheable
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

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime created_on;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime last_modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientID() {
        return client_id;
    }

    public void setClientID(Integer client_id) {
        this.client_id = client_id;
    }

    public Integer getPackageID() {
        return package_id;
    }

    public void setPackageID(Integer package_id) {
        this.package_id = package_id;
    }

    public Integer getLimitCount() {
        return limit_count;
    }

    public void setLimitCount(Integer limit_count) {
        this.limit_count = limit_count;
    }

    public String getLimitType() {
        return limit_type;
    }

    public void setLimitType(String limit_type) {
        this.limit_type = limit_type;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Float getIncomingPackets() {
        return incoming_packets;
    }

    public void setIncomingPackets(Float incoming_packets) {
        this.incoming_packets = incoming_packets;
    }

    public Float getOutgoingPackets() {
        return outgoing_packets;
    }

    public void setOutgoingPackets(Float outgoing_packets) {
        this.outgoing_packets = outgoing_packets;
    }

    public LocalDateTime getCreatedOn() {
        return created_on;
    }

    public void setCreatedOn(LocalDateTime created_on) {
        this.created_on = created_on;
    }

    public LocalDateTime getLastModified() {
        return last_modified;
    }

    public void setLastModified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }

}