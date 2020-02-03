package com.comavp.infsystem.controllers;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Genre;
import com.comavp.infsystem.service.GenreService;
import com.comavp.infsystem.service.iservice.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class GenreController {

    private IGenreService genreService;
    private String filterMethod = "All";

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @RequestMapping(value = { "/genreList" }, method = RequestMethod.GET)
    public String genreList(Model model) {
        model.addAttribute("genres", filter());
        return "genreList";
    }

    @RequestMapping(value = "/genreFilter/{filterMethod}", method = RequestMethod.GET)
    public String chooseFilter(@PathVariable String filterMethod) {
        this.filterMethod = filterMethod;
        return "redirect:/genreList";
    }

    private List<Genre> filter() {
        List<Genre> genres = null;
        switch (filterMethod) {
            case "All":
                genres = genreService.findAll();
                break;
            case "Raiting":
                genres = genreService.findAllByOrderByRaitingAsc();
        }
        return genres;
    }

    @RequestMapping(value = { "/addGenre" }, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showAddGenrePage(Model model) {
        return "addGenre";
    }

    @RequestMapping(value = {"/addGenre"}, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String addGenre(@RequestParam String name, @RequestParam String raiting) {
        genreService.saveGenre(new Genre(name, toIntegerSafe(raiting)));
        return "redirect:/genreList";
    }

    @RequestMapping(value = "/deleteGenre/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String deleteGenre(@PathVariable Integer id) {
        for (Artist artist : genreService.getGenreById(id).getArtists()) {
            artist.getGenres().remove(genreService.getGenreById(id));
        }

        genreService.deleteGenre(id);
        return "redirect:/genreList";
    }

    @RequestMapping(value = "/editGenre/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showEditGenrePage(@PathVariable Integer id, Model model) {
        Genre genre = genreService.getGenreById(id);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @RequestMapping(value = "/editGenre", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String editGenre(@RequestParam Integer id, @RequestParam String name, @RequestParam String raiting) {
        genreService.updateGenre(id, name, toIntegerSafe(raiting));
        return "redirect:/genreList";
    }

    private int toIntegerSafe(String arg) {
        int tmLength;
        try {
            tmLength = Integer.parseInt(arg);
            if (tmLength < 0) {
                tmLength = 0;
            }
        } catch (Exception e) {
            tmLength = 0;
        }
        return tmLength;
    }
}
