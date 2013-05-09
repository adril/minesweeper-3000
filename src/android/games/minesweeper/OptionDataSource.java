package android.games.minesweeper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OptionDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySqliteHelper dbHelper;
  private String[] allColumns = { MySqliteHelper.COLUMN_ID,
	      MySqliteHelper.COLUMN_NAME,
	      MySqliteHelper.COLUMN_LEVEL,
	      MySqliteHelper.COLUMN_SIZE };

  public OptionDataSource(Context context) {
    dbHelper = new MySqliteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Option createOption(String name, long size, long level) {
    ContentValues values = new ContentValues();
    values.put(MySqliteHelper.COLUMN_NAME, name);
    values.put(MySqliteHelper.COLUMN_LEVEL, level);
    values.put(MySqliteHelper.COLUMN_SIZE, size);
    long insertId = database.insert(MySqliteHelper.TABLE_OPTIONS, null,
        values);
    Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns,
        MySqliteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Option newOption = cursorToOption(cursor);
    cursor.close();
    Log.d("Options", "Option created with id: " + newOption.getId());
    return newOption;
  }

  public Option updateOption(Option option) {
	String strFilter = "_id=" + option.getId();
    ContentValues values = new ContentValues();
    values.put(MySqliteHelper.COLUMN_NAME, option.getName());
    values.put(MySqliteHelper.COLUMN_LEVEL, option.getLevel());
    values.put(MySqliteHelper.COLUMN_SIZE, option.getSize());
    long insertId = database.insert(MySqliteHelper.TABLE_OPTIONS, strFilter, values);
    Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns,
        MySqliteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Option updatedOption = cursorToOption(cursor);
    cursor.close();
    Log.d("Options", "Option updated with id: " + updatedOption.getId());
    return updatedOption;
  }

  public void deleteOption(Option Option) {
    long id = Option.getId();
    Log.d("Options", "Option deleted with id: " + id);
    database.delete(MySqliteHelper.TABLE_OPTIONS, MySqliteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<Option> getAllOptions() {
    List<Option> Options = new ArrayList<Option>();

    Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Option Option = cursorToOption(cursor);
      Options.add(Option);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return Options;
  }

  public void deleteAllOptions() {
    List<Option> options = getAllOptions();
    for (int i = 0; i < options.size(); i++)
    	deleteOption(options.get(i));
  }

  public Option getOption() {
	Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns, null, null, null, null, null);
    cursor.moveToFirst();
    Option option = cursorToOption(cursor);
    cursor.close();
    return option;
  }

  private Option cursorToOption(Cursor cursor) {
    Option Option = new Option();
    Option.setId(cursor.getLong(0));
    Option.setName(cursor.getString(1));
    Option.setSize(cursor.getLong(2));
    Option.setLevel(cursor.getLong(3));
    return Option;
  }
}
