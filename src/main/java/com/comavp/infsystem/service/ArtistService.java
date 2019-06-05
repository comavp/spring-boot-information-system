package com.comavp.infsystem.service;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Genre;
import com.comavp.infsystem.repositories.ArtistRepository;
import com.comavp.infsystem.service.iservice.IArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArtistService implements IArtistService {

    private ArtistRepository artistRepository;

    @Autowired
    public void setArtistRepository(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public Artist getArtistById(Integer id) {
        return artistRepository.getOne(id);
    }

    @Override
    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }

    @Override
    public void updateArtist(Integer id, String name, String country, int age) {
        Artist updated = artistRepository.getOne(id);
        if (!name.equals("")) {
            updated.setName(name);
        }
        if (!country.equals("")) {
            updated.setCountry(country);
        }
        if (age != 0) {
            updated.setAge(age);
        }
        artistRepository.save(updated);
    }

    @Override
    public void deleteArtist(Integer id) {
        artistRepository.delete(artistRepository.getOne(id));
    }

    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public List<Artist> findAllByOrderByAgeAsc() {
        return artistRepository.findAllByOrderByAgeAsc();
    }

    @Override
    public List<Artist> findAllByOrderByCountryAsc() {
        return artistRepository.findAllByOrderByCountryAsc();
    }

    @Override
    public List<Artist> findArtistsByGenres(Genre genre) {
        return artistRepository.findArtistsByGenres(genre);
    }
}
