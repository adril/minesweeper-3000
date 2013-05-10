package android.games.minesweeper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

// Database data accessor for the options resources in Options table
// There should always be one and only one record in this table.
//
// From anywhere in the application, access OptionDataSource (which
// give access to the options records in the database) using:
// optionDataSource = ((Globals)getApplication()).getOptionDataSource();
public class OptionDataSource implements IDataSource {

	private String TAG = this.toString();

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
		String strFilter = MySqliteHelper.COLUMN_ID + " = " + insertId;
		Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns,
				strFilter, null, null, null, null);
		cursor.moveToFirst();
		Option newOption = cursorToOption(cursor);
		cursor.close();
		Log.d(TAG, "Option created: " + newOption.toString());
		return newOption;
	}

	public Option updateOption(Option option) {
		ContentValues values = new ContentValues();
		values.put(MySqliteHelper.COLUMN_NAME, option.getName());
		values.put(MySqliteHelper.COLUMN_LEVEL, option.getLevel());
		values.put(MySqliteHelper.COLUMN_SIZE, option.getSize());
		String strFilter = MySqliteHelper.COLUMN_ID + " = " + option.getId();
		database.update(MySqliteHelper.TABLE_OPTIONS, values, strFilter, null);
		Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns,
				strFilter, null, null, null, null);
		cursor.moveToFirst();
		Option updatedOption = cursorToOption(cursor);
		cursor.close();
		Log.d(TAG, "Option updated: " + updatedOption.toString());
		return updatedOption;
	}

	public void deleteOption(Option option) {
		String strFilter = MySqliteHelper.COLUMN_ID + " = " + option.getId();
		Log.d(TAG, "Option deleted: " + option.toString());
		database.delete(MySqliteHelper.TABLE_OPTIONS, strFilter, null);
	}

	public List<Option> getAllOptions() {
		List<Option> options = new ArrayList<Option>();
		Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Option option = cursorToOption(cursor);
			options.add(option);
			Log.d(TAG, "Get option: " + option.toString());
			cursor.moveToNext();
		}
		cursor.close();
		return options;
	}

	public void deleteAllOptions() {
		Log.d(TAG, "Deleting all options records");
		List<Option> options = getAllOptions();
		for (int i = 0; i < options.size(); i++)
			deleteOption(options.get(i));
	}

	public Option getOption() {
		Cursor cursor = database.query(MySqliteHelper.TABLE_OPTIONS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		Option option = cursorToOption(cursor);
		cursor.close();
		Log.d(TAG, "Get option: " + option.toString());
		return option;
	}

	private Option cursorToOption(Cursor cursor) {
		Option option = new Option();
		option.setId(cursor.getLong(0));
		option.setName(cursor.getString(1));
		option.setSize(cursor.getInt(2));
		option.setLevel(cursor.getInt(3));
		return option;
	}

	@Override
	public void seed() {
		Log.d(TAG, "Seeding");
		int optionsCount = getAllOptions().size();
		if (optionsCount == 0)
			createOption("Default player", game_size.GAME_SIZE_SMALL.ordinal(), level.LEVEL_EASY.ordinal());
		else if (optionsCount > 1) {
			Option tmpOption = getOption();
			deleteAllOptions();
			createOption(tmpOption.getName(), tmpOption.getSize(), tmpOption.getLevel());
		}
	}
}
