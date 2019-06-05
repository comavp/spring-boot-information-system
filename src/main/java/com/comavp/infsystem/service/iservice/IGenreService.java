package com.comavp.infsystem.service.iservice;

import com.comavp.infsystem.entities.Genre;
import java.util.List;

public interface IGenreService {
    Genre getGenreById(Integer id);
    void saveGenre(Genre artist);
    void updateGenre(Integer id, String name, int raiting);
    void deleteGenre(Integer id);
    List<Genre> findAll();
    List<Genre> findAllByOrderByRaitingAsc();
}
