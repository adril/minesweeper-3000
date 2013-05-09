package android.games.minesweeper;

import android.database.SQLException;

public interface IDataSource {
	public void open() throws SQLException;
	public void close();
	public void seed();
}
