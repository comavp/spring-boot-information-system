package com.comavp.infsystem.repositories;

import com.comavp.infsystem.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findAllByOrderByRaitingAsc();
}
