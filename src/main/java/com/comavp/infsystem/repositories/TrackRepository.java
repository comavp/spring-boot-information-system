package com.comavp.infsystem.repositories;

import com.comavp.infsystem.entities.Artist;
import com.comavp.infsystem.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer> {

    List<Track> findAllByOrderByLengthAsc();
    List<Track> findAllByOrderByAlbumAsc();
    List<Track> findTracksByArtists(Artist artist);

//    //@Query(value = "select t from Track t where t.length < 30")
//    @Query(value = "select t from Track t inner join artist_track at on t.id = at.track_id inner join Artist a on at.artist_id = t.id where a.name = ?1",
//            nativeQuery = true)
//    List<Track> findTracksByArtistsByLengthAsc(String name);
    List<Track> findTracksByAlbum(String album);
}
