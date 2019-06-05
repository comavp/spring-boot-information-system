package com.comavp.infsystem.service;

import com.comavp.infsystem.entities.Genre;
import com.comavp.infsystem.repositories.GenreRepository;
import com.comavp.infsystem.service.iservice.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GenreService implements IGenreService {

    private GenreRepository genreRepository;

    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreRepository.getOne(id);
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void updateGenre(Integer id, String name, int raiting) {
        Genre updated = genreRepository.getOne(id);
        if (!name.equals("")) {
            updated.setName(name);
        }
        if (raiting != 0) {
            updated.setRaiting(raiting);
        }
        genreRepository.save(updated);
    }

    @Override
    public void deleteGenre(Integer id) {
        genreRepository.delete(genreRepository.getOne(id));
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public List<Genre> findAllByOrderByRaitingAsc() {
        return genreRepository.findAllByOrderByRaitingAsc();
    }
}
