package com.comavp.infsystem.service;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Track;
import com.comavp.infsystem.repositories.TrackRepository;
import com.comavp.infsystem.service.iservice.ITrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackService implements ITrackService {

    private TrackRepository trackRepository;

    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track getTrackById(Integer id) {
        return trackRepository.getOne(id);
    }

    @Override
    public void saveTrack(Track track) {
        trackRepository.save(track);
    }

    @Override
    public void updateTrack(Integer id, String name, String album, int length) {
        Track updated = trackRepository.getOne(id);
        if (!name.equals("")) {
            updated.setName(name);
        }
        if (!album.equals("")) {
            updated.setAlbum(album);
        }
        if (length != 0) {
            updated.setLength(length);
        }
        trackRepository.save(updated);
    }

    @Override
    public void deleteTrack(Integer id) {
        trackRepository.delete(trackRepository.getOne(id));
    }

    @Override
    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    @Override
    public List<Track> findAllByOrderByLengthAsc() {
        return trackRepository.findAllByOrderByLengthAsc();
    }

    @Override
    public List<Track> findAllByOrderByAlbumAsc() {
        return trackRepository.findAllByOrderByAlbumAsc();
    }

    @Override
    public List<Track> findTracksByArtists(Artist artist) {
        return trackRepository.findTracksByArtists(artist);
    }

    @Override
    public List<Track> findTracksByAlbum(String album) {
        return trackRepository.findTracksByAlbum(album);
    }

//    @Override
//    public List<Track> findTracksByArtistsByLengthAsc(String name) {
//        return trackRepository.findTracksByArtistsByLengthAsc(name);
//    }
}
