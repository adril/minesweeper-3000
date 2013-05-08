package android.games.minesweeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper {

  public static final String TABLE_HIGHSCORES = "highscores";
  public static final String TABLE_OPTIONS = "options";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_HIGHSCORE = "score";
  public static final String COLUMN_NAME = "name";
  public static final String COLUMN_DATE = "date";
  public static final String COLUMN_DURATION = "duration";
  public static final String COLUMN_SIZE = "size";
  public static final String COLUMN_LEVEL = "level";

  private static final String DATABASE_NAME = "minesweeper.db";
  private static final int DATABASE_VERSION = 7;

  // Database creation sql statement
  private static final String DATABASE_CREATE_HIGHSCORES = "create table "
      + TABLE_HIGHSCORES + "(" + COLUMN_ID
      + " integer primary key autoincrement, "
      + COLUMN_HIGHSCORE + " text not null,"
      + COLUMN_NAME + " text not null,"
      + COLUMN_DATE + " datetime default current_timestamp,"
      + COLUMN_DURATION + " integer not null,"
      + COLUMN_SIZE + " integer not null,"
      + COLUMN_LEVEL + " integer not null"
      + ");";
  private static final String DATABASE_CREATE_OPTIONS = "create table "
      + TABLE_OPTIONS + "(" + COLUMN_ID
      + " integer primary key autoincrement, "
      + COLUMN_NAME + " text not null,"
      + COLUMN_SIZE + " integer not null,"
      + COLUMN_LEVEL + " integer not null"
      + ");";

  public MySqliteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE_HIGHSCORES);
	    database.execSQL(DATABASE_CREATE_OPTIONS);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySqliteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORES);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);
    onCreate(db);
  }

}