insert into tracks (track_id, track_album, track_name, track_length ) values(1, 'Album1', 'Track1', 10);
insert into tracks (track_id, track_album, track_name, track_length) values(2, 'Album2', 'Track2', 8);
insert into tracks (track_id, track_album, track_name, track_length ) values(3, 'Album3',  'Track3', 3);
insert into tracks (track_id, track_album, track_name, track_length ) values(4, 'Album4', 'Track4', 50);
insert into tracks (track_id, track_album, track_name, track_length) values(5, 'Album5', 'Track5', 3);
insert into tracks (track_id, track_album, track_name, track_length ) values(6, 'Album6',  'Track6', 2);
insert into tracks (track_id, track_album, track_name, track_length ) values(7, 'Album7', 'Track7', 10);
insert into tracks (track_id, track_album, track_name, track_length) values(8, 'Album2', 'Track8', 8);
insert into tracks (track_id, track_album, track_name, track_length ) values(9, 'Album3',  'Track9', 3);
insert into tracks (track_id, track_album, track_name, track_length ) values(10, 'Album1', 'Track10', 50);
insert into tracks (track_id, track_album, track_name, track_length) values(11, 'Album2', 'Track11', 3);
insert into tracks (track_id, track_album, track_name, track_length ) values(12, 'Album3',  'Track12', 2);

insert into artists (artist_id, artist_name, artist_country, artist_age) values (1, 'Artist1', 'Country1', 34);
insert into artists (artist_id, artist_name, artist_country, artist_age) values (2, 'Artist2', 'Country2', 26);
insert into artists (artist_id, artist_name, artist_country, artist_age) values (3, 'Artist3', 'Country3', 38);
insert into artists (artist_id, artist_name, artist_country, artist_age) values (4, 'Artist4', 'Country4', 49);
insert into artists (artist_id, artist_name, artist_country, artist_age) values (5, 'Artist5', 'Country5', 27);
insert into artists (artist_id, artist_name, artist_country, artist_age) values (6, 'Artist6', 'Country6', 50);

insert into genres (genre_id, genre_name, genre_raiting) values (1, 'Genre1', 35);
insert into genres (genre_id, genre_name, genre_raiting) values (2, 'Genre2', 45);
insert into genres (genre_id, genre_name, genre_raiting) values (3, 'Genre3', 89);

insert into artist_track (track_id, artist_id) values (1, 1);
insert into artist_track (track_id, artist_id) values (2, 1);
insert into artist_track (track_id, artist_id) values (3, 2);
insert into artist_track (track_id, artist_id) values (4, 2);
insert into artist_track (track_id, artist_id) values (5, 3);
insert into artist_track (track_id, artist_id) values (6, 3);
insert into artist_track (track_id, artist_id) values (7, 4);
insert into artist_track (track_id, artist_id) values (8, 4);
insert into artist_track (track_id, artist_id) values (9, 5);
insert into artist_track (track_id, artist_id) values (10, 5);
insert into artist_track (track_id, artist_id) values (11, 6);
insert into artist_track (track_id, artist_id) values (12, 6);

insert into genre_artist (artist_id, genre_id) values (1, 1);
insert into genre_artist (artist_id, genre_id) values (2, 1);
insert into genre_artist (artist_id, genre_id) values (3, 2);
insert into genre_artist (artist_id, genre_id) values (4, 2);
insert into genre_artist (artist_id, genre_id) values (5, 3);
insert into genre_artist (artist_id, genre_id) values (6, 3);
