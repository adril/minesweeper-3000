package android.games.minesweeper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// Database data accessor for the scores resources in HighScores table
//
public class ScoreDataSource {

	// Database fields
	private SQLiteDatabase database;
	private MySqliteHelper dbHelper;
	private String[] allColumns = { MySqliteHelper.COLUMN_ID,
			MySqliteHelper.COLUMN_HIGHSCORE,
			MySqliteHelper.COLUMN_NAME,
			MySqliteHelper.COLUMN_DATE,
			MySqliteHelper.COLUMN_DURATION,
			MySqliteHelper.COLUMN_LEVEL,
			MySqliteHelper.COLUMN_SIZE };

	public ScoreDataSource(Context context) {
		dbHelper = new MySqliteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	@SuppressLint("SimpleDateFormat")
	public Score createScore(long score, String name, long size, long level, long duration) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
		ContentValues values = new ContentValues();
		values.put(MySqliteHelper.COLUMN_HIGHSCORE, score);
		values.put(MySqliteHelper.COLUMN_NAME, name);
		values.put(MySqliteHelper.COLUMN_LEVEL, level);
		values.put(MySqliteHelper.COLUMN_SIZE, size);
		values.put(MySqliteHelper.COLUMN_DATE, dateFormat.format(date));
		values.put(MySqliteHelper.COLUMN_DURATION, duration);
		long insertId = database.insert(MySqliteHelper.TABLE_HIGHSCORES, null,
				values);
		String strFilter = MySqliteHelper.COLUMN_ID + " = " + insertId;
		Cursor cursor = database.query(MySqliteHelper.TABLE_HIGHSCORES, allColumns,
				strFilter, null, null, null, null);
		cursor.moveToFirst();
		Score newScore = cursorToScore(cursor);
		Log.d("Database", "Score created with id: " + newScore.getId());
		cursor.close();
		return newScore;
	}

	public Score updateScore(Score score) {
		ContentValues values = new ContentValues();
		values.put(MySqliteHelper.COLUMN_HIGHSCORE, score.getScore());
		values.put(MySqliteHelper.COLUMN_NAME, score.getName());
		values.put(MySqliteHelper.COLUMN_LEVEL, score.getLevel());
		values.put(MySqliteHelper.COLUMN_SIZE, score.getSize());
		values.put(MySqliteHelper.COLUMN_DATE, score.getDate());
		values.put(MySqliteHelper.COLUMN_DURATION, score.getDuration());
		String strFilter = MySqliteHelper.COLUMN_ID + " = " + score.getId();
		long insertId = database.update(MySqliteHelper.TABLE_HIGHSCORES, values, strFilter, null);
		Cursor cursor = database.query(MySqliteHelper.TABLE_HIGHSCORES, allColumns,
				MySqliteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Score newScore = cursorToScore(cursor);
		Log.d("Database", "Score updated with id: " + newScore.getId());
		cursor.close();
		return newScore;
	}

	public void deleteScore(Score Score) {
		long id = Score.getId();
		Log.d("Database", "Score deleted with id: " + id);
		database.delete(MySqliteHelper.TABLE_HIGHSCORES, MySqliteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Score> getAllScores() {
		List<Score> Scores = new ArrayList<Score>();

		Cursor cursor = database.query(MySqliteHelper.TABLE_HIGHSCORES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Score Score = cursorToScore(cursor);
			Scores.add(Score);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return Scores;
	}

	public void deleteAllScores() {
		List<Score> scores = getAllScores();
		for (int i = 0; i < scores.size(); i++)
			deleteScore(scores.get(i));
	}

	private Score cursorToScore(Cursor cursor) {
		Score Score = new Score();
		Score.setId(cursor.getLong(0));
		Score.setScore(cursor.getLong(1));
		Score.setName(cursor.getString(2));
		Score.setDuration(cursor.getLong(4));
		Score.setSize(cursor.getLong(6));
		Score.setLevel(cursor.getLong(5));
		Score.setDate(cursor.getString(3));
		return Score;
	}
}
