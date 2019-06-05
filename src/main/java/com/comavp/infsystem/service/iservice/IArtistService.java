package com.comavp.infsystem.service.iservice;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Genre;

import java.util.List;

public interface IArtistService {

    Artist getArtistById(Integer id);
    void saveArtist(Artist artist);
    void updateArtist(Integer id, String name, String country, int age);
    void deleteArtist(Integer id);
    List<Artist> findAll();
    List<Artist> findAllByOrderByAgeAsc();
    List<Artist> findAllByOrderByCountryAsc();

    List<Artist> findArtistsByGenres(Genre genre);
}
