package com.comavp.infsystem.repositories;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    List<Artist> findAllByOrderByAgeAsc();
    List<Artist> findAllByOrderByCountryAsc();

    List<Artist> findArtistsByGenres(Genre genre);
}
