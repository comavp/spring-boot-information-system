package com.comavp.infsystem.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracks")
public class Track {

    @Id
    @Column(name = "track_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "track_sequence")
    @SequenceGenerator(name = "track_sequence", sequenceName = "TRACK_SEQ", initialValue = 13)
    private Integer id;

    @Column(name = "track_name")
    private String name;

    @Column(name = "track_album")
    private String album;

    @Column(name = "track_length")
    private int length;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            })
    @JoinTable(name = "artist_track",
            joinColumns = {@JoinColumn(name = "track_id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id")})
    private List<Artist> artists = new ArrayList<Artist>();

    public Track() {

    }

    public Track(String name, String album, int length) {
        this.name = name;
        this.album = album;
        this.length = length;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}