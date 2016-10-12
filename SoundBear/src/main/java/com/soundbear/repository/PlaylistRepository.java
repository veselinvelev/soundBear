package com.soundbear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.soundbear.model.app.Playlist;

@Repository
public class PlaylistRepository implements PlaylistDAO {
	private static final String IS_SONG_IN_PLAYLIST_SQL = "SELECT count(*) FROM playlists_has_songs WHERE playlist_id = ? AND song_id = ?";
	private static final String DELETE_SONG_FROM_PLAYLIST_SQL = "DELETE FROM playlists_has_songs WHERE playlist_id = ? AND song_id = ?";
	private static final String DELETE_PLAYLIST_SQL = "DELETE FROM playlists WHERE playlist_id = ?";
	private static final String LIST_PLAYLISTS_SQL = "SELECT * FROM playlists WHERE user_id = ?";
	private static final String ADD_SONG_INTO_PLAYLIST_SQL = "INSERT INTO playlists_has_songs VALUES (?, ?)";
	private static final String ADD_PLAYLIST_SQL = "INSERT INTO playlists VALUES (null, ?, ?)";

	private JdbcTemplate jdbcTemplate;

	public PlaylistRepository() {

	}

	@Autowired
	public PlaylistRepository(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addPlaylist(Playlist playlist) {
		jdbcTemplate.update(ADD_PLAYLIST_SQL, playlist.getPlaylistName(), playlist.getUserId());
	}

	@Override
	public void addSong(int playlistId, int songId) {
		jdbcTemplate.update(ADD_SONG_INTO_PLAYLIST_SQL, playlistId, songId);
	}

	@Override
	public List<Playlist> listPlaylists(int userId) {
		List<Playlist> playlists = jdbcTemplate.query(LIST_PLAYLISTS_SQL, new Object[] { userId },
				new PlaylistMapper());

		return playlists;
	}

	@Override
	public void deletePlaylist(int playlistId) {
		jdbcTemplate.update(DELETE_PLAYLIST_SQL, playlistId);
	}

	@Override
	public void deleteSong(int playlistId, int songId) {
		jdbcTemplate.update(DELETE_SONG_FROM_PLAYLIST_SQL, playlistId, songId);
	}

	@Override
	public boolean isSongInPlaylist(int playlistId, int songId) {
		Integer result = jdbcTemplate.queryForObject(IS_SONG_IN_PLAYLIST_SQL, new Object[] { playlistId, songId },
				Integer.class);

		return result != 0 ? true : false;
	}

	public class PlaylistMapper implements RowMapper<Playlist> {

		public Playlist mapRow(ResultSet rs, int rowNum) throws SQLException {

			Playlist playlist = new Playlist(rs.getInt("playlist_id"), rs.getString("playlist_name"),
					rs.getInt("user_id"));

			return playlist;
		}
	}

}
