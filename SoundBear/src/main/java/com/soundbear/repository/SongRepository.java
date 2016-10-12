package com.soundbear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.soundbear.model.app.Song;

@Repository
public class SongRepository implements SongDAO {
	private static final String GET_GENRE_SQL = "SELECT genre_id FROM genres WHERE genre_name = ?";
	private static final String GET_ARTIST_SQL = "SELECT artist_id FROM artists WHERE artist_name = ?";
	private static final String ADD_ARTIST_SQL = "INSERT INTO artists values (null, ?)";
	private static final String ADD_SONG_SQL = "INSERT INTO songs VALUES (null, ?, ?, ?, ?, ?)";
	private static final String LIST_SONGS_SQL = "SELECT * FROM songs s \r\n"
			+ "JOIN artists a ON s.artist_id = a.artist_id\r\n" + "JOIN genres g ON s.genre_id = g.genre_id;";
	private static final String LIST_SONGS_BY_KEY_SQL = "SELECT * FROM songs s JOIN artists a ON s.artist_id = a.artist_id\r\n"
			+ "JOIN genres g ON s.genre_id = g.genre_id\r\n" + "WHERE s.song_name LIKE ? OR a.artist_name LIKE ?";
	private static final String LIST_USER_SONGS_SQL = "SELECT * FROM songs s JOIN artists a ON s.artist_id = a.artist_id\r\n"
			+ "JOIN genres g ON s.genre_id = g.genre_id\r\n" + "WHERE s.user_id = ?";
	private static final String LIST_SONGS_BY_GENRE_SQL = "SELECT * FROM songs s JOIN artists a ON s.artist_id = a.artist_id\r\n"
			+ "JOIN genres g ON s.genre_id = g.genre_id\r\n" + "WHERE g.genre_name = ?";
	private static final String LIST_SONGS_BY_PLAYLIST_SQL = "SELECT * FROM songs s JOIN playlists_has_songs p ON s.song_id = p.song_id "
			+ "JOIN genres g ON s.genre_id = g.genre_id JOIN artists a ON s.artist_id = a.artist_id where p.playlist_id = ?";

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;

	public SongRepository() {

	}

	@Autowired
	public SongRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}

	@Override
	public void addSong(final Song song) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				try {
					int artistId = getArtistId(song.getArtist());

					if (artistId == 0) {
						addArtist(song.getArtist());
						artistId = getArtistId(song.getArtist());
					}

					int genreId = getGenreId(song.getGenre());

					jdbcTemplate.update(ADD_SONG_SQL, song.getSongName(), song.getPath(), song.getUserId(), genreId,
							artistId);
				}
				catch (Exception e) {
					transactionStatus.setRollbackOnly();
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public int getGenreId(String genreName) {
		return jdbcTemplate.queryForObject(GET_GENRE_SQL, new Object[] { genreName }, Integer.class);
	}

	@Override
	public int getArtistId(String artistName) {
		Integer result = 0;

		try {
			result = jdbcTemplate.queryForObject(GET_ARTIST_SQL, new Object[] { artistName }, Integer.class);
		}
		catch (EmptyResultDataAccessException e) {
			return result;
		}

		return result;
	}

	@Override
	public void addArtist(String artistName) {
		jdbcTemplate.update(ADD_ARTIST_SQL, artistName);
	}

	@Override
	public List<Song> listSongs() {
		List<Song> songs = jdbcTemplate.query(LIST_SONGS_SQL, new SongMapper());

		return songs;
	}

	@Override
	public List<Song> listSongs(String key) {
		List<Song> songs = jdbcTemplate.query(LIST_SONGS_BY_KEY_SQL,
				new Object[] { "%" + key.trim() + "%", "%" + key.trim() + "%" }, new SongMapper());

		return songs;
	}

	@Override
	public List<Song> listSongs(int userId) {
		List<Song> songs = jdbcTemplate.query(LIST_USER_SONGS_SQL, new Object[] { userId }, new SongMapper());

		return songs;
	}

	@Override
	public List<Song> listSongsByGenre(String genreName) {
		List<Song> songs = jdbcTemplate.query(LIST_SONGS_BY_GENRE_SQL, new Object[] { genreName }, new SongMapper());

		return songs;
	}

	@Override
	public List<Song> listSongsByPlaylist(int playlistId) {
		List<Song> songs = jdbcTemplate.query(LIST_SONGS_BY_PLAYLIST_SQL, new Object[] { playlistId }, new SongMapper());

		return songs;
	}

	public class SongMapper implements RowMapper<Song> {

		public Song mapRow(ResultSet rs, int rowNum) throws SQLException {

			Song song = new Song(rs.getInt("song_id"), rs.getString("song_name"), rs.getString("path"),
					rs.getInt("user_id"), rs.getString("genre_name"), rs.getString("artist_name"));

			return song;
		}
	}
}
