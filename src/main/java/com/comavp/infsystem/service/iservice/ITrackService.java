package com.comavp.infsystem.service.iservice;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Track;

import java.util.List;

public interface ITrackService {
    Track getTrackById(Integer id);
    void saveTrack(Track track);
    void updateTrack(Integer id, String name, String album, int length);
    void deleteTrack(Integer id);
    List<Track> findAll();
    List<Track> findAllByOrderByLengthAsc();
    List<Track> findAllByOrderByAlbumAsc();
    List<Track> findTracksByArtists(Artist artist);

    //List<Track> findTracksByArtistsByLengthAsc(String name);
}
