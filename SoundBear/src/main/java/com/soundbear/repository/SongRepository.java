package com.soundbear.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.soundbear.model.app.Song;

@Component
public class SongRepository implements SongDAO {
	private static final String GET_GENRE_SQL = "SELECT genre_id FROM genres WHERE genre_name = ?";
	private static final String GET_ARTIST_SQL = "SELECT artist_id FROM artists WHERE artist_name = ?";
	private static final String ADD_ARTIST_SQL = "INSERT INTO artists values (null, ?)";
	private static final String ADD_SONG_SQL = "INSERT INTO songs VALUES (null, ?, ?, ?, ?, ?)";

	private JdbcTemplate jdbcTemplate;
	private TransactionTemplate transactionTemplate;
	
	public SongRepository() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public SongRepository(DataSource dataSource, TransactionTemplate template) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		transactionTemplate = template;
	}

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
	public int getGenreId(String genreName) {
		return jdbcTemplate.queryForObject(GET_GENRE_SQL, new Object[] { genreName }, Integer.class);
	}

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
}
