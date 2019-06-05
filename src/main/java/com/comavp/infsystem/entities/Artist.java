package com.comavp.infsystem.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="artists")
public class Artist {
    @Id
    @Column(name = "artist_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "artist_sequence")
    @SequenceGenerator(name = "artist_sequence", sequenceName = "ARTIST_SEQ", initialValue = 6)
    private Integer id;

    @Column(name = "artist_name")
    private String name;

    @Column(name = "artist_country")
    private String country;

    @Column(name = "artist_age")
    private int age;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "artists")
    private List<Track> tracks = new ArrayList<Track>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            })
    @JoinTable(name = "genre_artist",
            joinColumns = {@JoinColumn(name = "artist_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres = new ArrayList<Genre>();

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Artist() {

    }

    public Artist(String name, String country, int age) {
        this.name = name;
        this.country = country;
        this.age = age;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
