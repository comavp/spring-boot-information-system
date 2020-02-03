package com.comavp.infsystem.controllers;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Genre;
import com.comavp.infsystem.entities.Track;
import com.comavp.infsystem.service.ArtistService;
import com.comavp.infsystem.service.GenreService;
import com.comavp.infsystem.service.iservice.IArtistService;
import com.comavp.infsystem.service.iservice.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ArtistController {

    private IArtistService artistService;
    private IGenreService genreService;

    private String filterMethod = "Id";
    private Integer currentGenreId = -1;

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Autowired
    public void setGenreService(GenreService genreService) { this.genreService = genreService; }

    @RequestMapping(value = { "/artistList" }, method = RequestMethod.GET)
    public String artistList(Model model) {
        model.addAttribute("artists", filter());
        return "artistList";
    }

    @RequestMapping(value = "/artistFilter/{filterMethod}", method = RequestMethod.GET)
    public String chooseFilter(@PathVariable String filterMethod) {
        this.filterMethod = filterMethod;
        return "redirect:/artistList";
    }

    private List<Artist> filter() {
        List<Artist> artists = null;
        if (currentGenreId > 0 && !filterMethod.equals("All")) {
            filterMethod += "Genre";
        }

        switch (filterMethod) {
            case "All":
                artists = artistService.findAll();
                currentGenreId = -1;
                break;
            case "Country":
                artists = artistService.findAllByOrderByCountryAsc();
                break;
            case "Age":
                artists = artistService.findAllByOrderByAgeAsc();
                break;
            case "IdGenre":
                artists = artistService.findArtistsByGenres(genreService.getGenreById(currentGenreId));
                break;
            case "CountryGenre":
                break;
            case "AgeGenre":
                break;
        }
        return artists;
    }

    @RequestMapping(value = { "/addArtist" }, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showAddArtistPage(Model model) {
        model.addAttribute("genres", genreService.findAll());

        return "addArtist";
    }

    @RequestMapping(value = {"/addArtist"}, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String addArtist(@RequestParam String name, @RequestParam String country, @RequestParam String age,
                            @RequestParam(value = "genresIdList", required = false) List<Integer> genreIdList) {
        Artist artist = new Artist(name, country, toIntegerSafe(age));

        if (genreIdList != null) {
            for (Integer id : genreIdList) {
                artist.getGenres().add(genreService.getGenreById(id));
                genreService.getGenreById(id).getArtists().add(artist);
            }
        }
        try {
            artistService.saveArtist(artist);
        } catch (Exception e) {
            artistService.saveArtist(artist);
        }
        filterMethod = "All";
        return "redirect:/artistList";
    }

    @RequestMapping(value = "/deleteArtist/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String deleteArtist(@PathVariable Integer id) {
        for (Track track : artistService.getArtistById(id).getTracks()) {
            track.getArtists().remove(artistService.getArtistById(id));
        }
        artistService.deleteArtist(id);
        return "redirect:/artistList";
    }

    @RequestMapping(value = "/editArtist/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showEditArtistPage(@PathVariable Integer id, Model model) {
        Artist artist = artistService.getArtistById(id);
        List<Genre> newGenres = genreService.findAll();
        List<Genre> oldGenres = artistService.getArtistById(id).getGenres();

        for (Genre genre: oldGenres) {
            if (newGenres.contains(genre)) {
                newGenres.remove(genre);
            }
        }

        model.addAttribute("artist", artist);
        model.addAttribute("oldGenres", oldGenres);
        model.addAttribute("genres", newGenres);
        return "editArtist";
    }

    @RequestMapping(value = "/editArtist", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String editArtist(@RequestParam Integer id, @RequestParam String name, @RequestParam String country, @RequestParam String age,
                             @RequestParam(value = "oldGenreIdList", required = false) List<Integer> oldGenreIdList,
                             @RequestParam(value = "genreIdList", required = false) List<Integer> genreIdList) {

        if (oldGenreIdList != null) {
            for (Integer it : oldGenreIdList) {
                artistService.getArtistById(id).getGenres().remove(genreService.getGenreById(it));
                genreService.getGenreById(it).getArtists().remove(artistService.getArtistById(id));
            }
        }

        if (genreIdList != null) {
            for (Integer it : genreIdList) {
                artistService.getArtistById(id).getGenres().add(genreService.getGenreById(it));
                genreService.getGenreById(it).getArtists().add(artistService.getArtistById(id));
            }
        }

        artistService.updateArtist(id, name, country, toIntegerSafe(age));
        return "redirect:/artistList";
    }

    @RequestMapping(value = "/getArtistsByGenre/{id}", method= RequestMethod.GET)
    public String getGenreArtists(@PathVariable Integer id) {
        this.currentGenreId = id;
        filterMethod = "Id";
        return "redirect:/artistList";
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
