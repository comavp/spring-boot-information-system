package com.comavp.infsystem.controllers;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Track;
import com.comavp.infsystem.service.ArtistService;
import com.comavp.infsystem.service.TrackService;
import com.comavp.infsystem.service.iservice.IArtistService;
import com.comavp.infsystem.service.iservice.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TrackController {

    private ITrackService trackService;
    private IArtistService artistService;

    private String filterMethod = "Id";
    private Integer currentArtistId = -1;
    private String currentAlbumName = "";

    @Autowired
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @RequestMapping(value = { "/trackList" }, method = RequestMethod.GET)
    public String trackList(Model model) {
        model.addAttribute("tracks", filter());
        return "trackList";
    }

    @RequestMapping(value = { "/addTrack" }, method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showAddTrackPage(Model model) {
        model.addAttribute("artists", artistService.findAll());
        return "addTrack";
    }

    @RequestMapping(value = {"/addTrack"}, method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String addTrack(@RequestParam String name, @RequestParam String album, @RequestParam String length,
                           @RequestParam(value = "artistIdList", required = false) List<Integer> artistIdList) {

        Track track = new Track(name, album, toIntegerSafe(length));

        if(artistIdList != null) {
            for (Integer id : artistIdList) {
                track.getArtists().add(artistService.getArtistById(id));
                artistService.getArtistById(id).getTracks().add(track);
            }
        }

        trackService.saveTrack(track);
        filterMethod = "All";
        return "redirect:/trackList";
    }

    @RequestMapping(value = "/deleteTrack/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String deleteTrack(@PathVariable Integer id) {
        trackService.deleteTrack(id);
        return "redirect:/trackList";
    }

    @RequestMapping(value = "/editTrack/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String showEditTrackPage(@PathVariable Integer id, Model model) {
        Track track = trackService.getTrackById(id);
        List<Artist> newArtists = artistService.findAll();
        List<Artist> oldArtists = track.getArtists();

        for (Artist artist: oldArtists) {
            if (newArtists.contains(artist)) {
                newArtists.remove(artist);
            }
        }

        model.addAttribute("track", track);
        model.addAttribute("oldArtists", oldArtists);
        model.addAttribute("artists", newArtists);
        return "editTrack";
    }

    @RequestMapping(value = "/editTrack", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('MODERATOR')")
    public String editTrack(@RequestParam Integer id, @RequestParam String name, @RequestParam String album, @RequestParam String length,
                            @RequestParam(value = "oldArtistIdList", required = false) List<Integer> oldArtistIdList,
                            @RequestParam(value = "artistIdList", required = false) List<Integer> artistIdList ) {

        if (oldArtistIdList != null) {
            for (Integer it : oldArtistIdList) {
                trackService.getTrackById(id).getArtists().remove(artistService.getArtistById(it));
                artistService.getArtistById(it).getTracks().remove(trackService.getTrackById(id));
            }
        }

        if (artistIdList != null) {
            for (Integer it : artistIdList) {
                trackService.getTrackById(id).getArtists().add(artistService.getArtistById(it));
                artistService.getArtistById(it).getTracks().add(trackService.getTrackById(id));
            }
        }

        trackService.updateTrack(id, name, album, toIntegerSafe(length));
        return "redirect:/trackList";
    }

    @RequestMapping(value = "/trackFilter/{filterMethod}", method = RequestMethod.GET)
    public String chooseFilter(@PathVariable String filterMethod) {
        this.filterMethod = filterMethod;
        return "redirect:/trackList";
    }

    private List<Track> filter() {
        List<Track> tracks = null;
        if (currentArtistId > 0 && !filterMethod.equals("All")) {
            filterMethod += "Artist";
        }

        switch (filterMethod) {
            case "All":
                tracks = trackService.findAll();
                currentArtistId = -1;
                break;
            case "Album":
                tracks = trackService.findAllByOrderByAlbumAsc();
                break;
            case "Length":
                tracks = trackService.findAllByOrderByLengthAsc();
                break;
            case "IdArtist":
                tracks = trackService.findTracksByArtists(artistService.getArtistById(currentArtistId));
                break;
            case "LengthArtist":
                //tracks = trackService.findTracksByArtistsByLengthAsc("Artist1");
                break;
            case "AlbumArtist":
                break;
            case "Find":
                tracks = trackService.findTracksByAlbum(currentAlbumName);
                break;
        }
        return tracks;
    }

    @RequestMapping(value = "/getTracksByArtist/{id}", method= RequestMethod.GET)
    public String getArtistTracks(@PathVariable Integer id) {
        this.currentArtistId = id;
        filterMethod = "Id";
        return "redirect:/trackList";
    }

    @RequestMapping(value="/findTrack", method = RequestMethod.GET)
    public String showFindTrackPage() {
        return "findTrack";
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

    @RequestMapping(value="findTrack", method= RequestMethod.POST)
    public String findTrackByAlbum(@RequestParam String name) {
        currentAlbumName = name;
        filterMethod = "Find";
        return "redirect:/trackList";
    }
}
