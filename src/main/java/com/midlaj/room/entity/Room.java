package com.midlaj.room.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resort_id", nullable = false)
    private Long resortId;

    @Column(nullable = false, length = 50, name = "room_code")
    private String roomCode;

    @Column(nullable = false, length = 1024)
    private String description;

    @Column(nullable = false)
    private Boolean enabled;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false, name = "created_time")
    private Date createdTime;

    @Column(nullable = false, name = "updated_time")
    private Date updateTime;

    @Column(nullable = false, name = "room_create_status")
    @Enumerated(EnumType.STRING)
    private RoomCreateStatus roomCreateStatus;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_facilities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private Set<RoomFacility> facilities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private RoomType roomType;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoomImages> images = new HashSet<>();

    public Room () {

    }

    public Room(Long id, Long resortId, String roomCode, String description, Boolean enabled, Float price, Date createdTime, Date updateTime, Set<RoomFacility> facilities, RoomType roomType, Set<RoomImages> images) {
        this.id = id;
        this.resortId = resortId;
        this.roomCode = roomCode;
        this.description = description;
        this.enabled = enabled;
        this.price = price;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
        this.facilities = facilities;
        this.roomType = roomType;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResortId() {
        return resortId;
    }

    public void setResortId(Long resortId) {
        this.resortId = resortId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<RoomFacility> getFacilities() {
        return facilities;
    }

    public void setFacilities(Set<RoomFacility> facilities) {
        this.facilities = facilities;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Set<RoomImages> getImages() {
        return images;
    }

    public void setImages(Set<RoomImages> images) {
        this.images = images;
    }

    public RoomCreateStatus getRoomCreateStatus() {
        return roomCreateStatus;
    }

    public void setRoomCreateStatus(RoomCreateStatus roomCreateStatus) {
        this.roomCreateStatus = roomCreateStatus;
    }

    public void addImage(String originalFileName, String compressedFileName) {
        this.images.add(new RoomImages(originalFileName, compressedFileName, this));
    }


}