package android.games.minesweeper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// Database data accessor for the scores resources in HighScores table
//
// From anywhere in the application, access ScoreDataSource (which
// give access to the scores records in the database) using:
// scoreDataSource = ((Globals)getApplication()).getScoreDataSource();
public class ScoreDataSource implements IDataSource {
	
	private String TAG = this.toString();

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
		Log.d(TAG, "Score created: " + newScore.toString());
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
		Log.d(TAG, "Score updated: " + newScore.toString());
		cursor.close();
		return newScore;
	}

	public void deleteScore(Score score) {
		long id = score.getId();
		Log.d(TAG, "Score deleted: " + score.toString());
		database.delete(MySqliteHelper.TABLE_HIGHSCORES, MySqliteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Score> getAllScores() {
		List<Score> scores = new ArrayList<Score>();
		Cursor cursor = database.query(MySqliteHelper.TABLE_HIGHSCORES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Score score = cursorToScore(cursor);
			scores.add(score);
			Log.d(TAG, "Get score: " + score.toString());
			cursor.moveToNext();
		}
		cursor.close();
		return scores;
	}

	public List<Score> getScoresByLevel(int level) {
		ContentValues values = new ContentValues();
		values.put(MySqliteHelper.COLUMN_LEVEL, level);
		List<Score> scores = new ArrayList<Score>();
		Cursor cursor = database.query(MySqliteHelper.TABLE_HIGHSCORES,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Score score = cursorToScore(cursor);
			if (score.getLevel() == level) {
				scores.add(score);
				Log.d(TAG, "Get score: " + score.toString());
			}
			cursor.moveToNext();
		}
		cursor.close();
		return scores;
	}
	
	public Map<String, List<Score>> getGroupedScores() {
		Map<String, List<Score>> groupedScores = new HashMap<String, List<Score>>();		
		
		int lvl = level.values().length;
		while (lvl != 0) {
			groupedScores.put(Globals.levelToString(lvl), getScoresByLevel(lvl));
			lvl--;
		}
		
		Set<Map.Entry<String, List<Score>>> s = groupedScores.entrySet();
		Iterator<Entry<String, List<Score>>> it = s.iterator();

        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
        	Entry<String, List<Score>> m =(Entry<String, List<Score>>)it.next();

            // getKey is used to get key of Map
            String key=(String)m.getKey();

            // getValue is used to get value of key in Map
            List<Score> value=(List<Score>)m.getValue();
            
            Log.d(TAG, "GroupedScoresMap(Key :"+key+" Value :"+value+")");
        }
		
		return groupedScores;
	}

	public void deleteAllScores() {
		Log.d(TAG, "Deleting all scores records");
		List<Score> scores = getAllScores();
		for (int i = 0; i < scores.size(); i++)
			deleteScore(scores.get(i));
	}

	private Score cursorToScore(Cursor cursor) {
		Score Score = new Score();
		Score.setId(cursor.getLong(0));
		Score.setScore(cursor.getInt(1));
		Score.setName(cursor.getString(2));
		Score.setDuration(cursor.getInt(4));
		Score.setSize(cursor.getInt(6));
		Score.setLevel(cursor.getInt(5));
		Score.setDate(cursor.getString(3));
		return Score;
	}

	@Override
	public void seed() {
		Log.d(TAG, "Seeding");
		deleteAllScores();
		if (getAllScores().size() == 0) {
			createScore(123456, "GOD", game_size.GAME_SIZE_BIG.ordinal(), level.LEVEL_HARD.ordinal(), 12);
		}
	}
}
