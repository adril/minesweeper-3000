package android.games.minesweeper;

import android.app.Application;

public class Globals extends Application {
	private ScoreDataSource scoreDataSource;
	private OptionDataSource optionDataSource;

	public void initializeDatabase() {
		this.scoreDataSource = new ScoreDataSource(this);
		this.optionDataSource = new OptionDataSource(this);
		this.scoreDataSource.open();
		this.optionDataSource.open();
	}

	public void closeDatabase() {
		this.scoreDataSource.close();
		this.optionDataSource.close();
	}

	public ScoreDataSource getScoreDataSource() {
		return this.scoreDataSource;
	}

	public OptionDataSource getOptionDataSource() {
		return this.optionDataSource;
	}
	
	public void seedDatabase() {
		this.optionDataSource.seed();
		this.scoreDataSource.seed();
	}

	public static String levelToString(int level) {
		switch (level) {
		case 0:
			return "easy";
		case 1:
			return "medium";
		case 2:
			return "hard";
		default:
			return "unknown";
		}
	}

	public static String gameSizeToString(int size) {
		switch (size) {
		case 0:
			return "small";
		case 1:
			return "medium";
		case 2:
			return "large";
		default:
			return "unknown";
		}
	}
	
	public static String toTitleCase(String string) {
		char[] charArray = string.toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		return new String(charArray);
	}
}
