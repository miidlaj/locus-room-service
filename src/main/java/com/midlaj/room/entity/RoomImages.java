package com.midlaj.room.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midlaj.room.util.Constants;
import jakarta.persistence.*;

@Entity
@Table(name = "room_images")
public class RoomImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_image", nullable = false)
    private String originalImage;

    @Column(name = "resized_image", nullable = false)
    private String resizedImage;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private Room room;

    public RoomImages() {
    }

    public RoomImages(Long id, String originalImage, String resizedImage, Room room) {
        this.id = id;
        this.originalImage = originalImage;
        this.resizedImage = resizedImage;
        this.room = room;
    }

    public RoomImages(String originalFileName, String compressedFileName, Room room) {
        this.originalImage = originalFileName;
        this.resizedImage = compressedFileName;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(String originalImage) {
        this.originalImage = originalImage;
    }

    public String getResizedImage() {
        return resizedImage;
    }

    public void setResizedImage(String resizedImage) {
        this.resizedImage = resizedImage;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Transient
    public String getOriginalImageLink(){
        return Constants.S3_BASE_URI + "/roomImages/" + room.getId() + "/" + this.originalImage;
    }

    @Transient
    public String getResizedImageLink(){
        return Constants.S3_BASE_URI + "/roomImages/" + room.getId() + "/" + this.resizedImage;
    }
}
