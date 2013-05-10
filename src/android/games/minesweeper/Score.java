package android.games.minesweeper;

public class Score {
	
	private String TAG = "Score";

	private long id;
	private int score;
	private String name;
	private int size;
	private String date;
	private int duration;
	private int level;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return TAG + " { _id: " + String.valueOf(id)
			+ ", score: " + String.valueOf(score)
			+ ", duration: " + String.valueOf(duration)
			+ ", level: " + String.valueOf(level)
			+ ", size: " + String.valueOf(size)
			+ ", date: " + date
			+ ", name: " + name + " }";
	}
}
