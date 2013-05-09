package android.games.minesweeper;

import android.app.Application;

public class Globals extends Application {
	private ScoreDataSource scoreDataSource;
	private OptionDataSource optionDataSource;

	public void initialize() {
		this.scoreDataSource = new ScoreDataSource(this);
		this.optionDataSource = new OptionDataSource(this);
		this.scoreDataSource.open();
		this.optionDataSource.open();
	}

	public void close() {
		this.scoreDataSource.close();
		this.optionDataSource.close();
	}

	public ScoreDataSource getScoreDataSource() {
		return this.scoreDataSource;
	}

	public OptionDataSource getOptionDataSource() {
		return this.optionDataSource;
	}
}
