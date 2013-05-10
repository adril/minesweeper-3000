package android.games.minesweeper;

enum level {
	LEVEL_EASY,
	LEVEL_MEDIUM,
	LEVEL_HARD
};

enum game_size {
	GAME_SIZE_SMALL,
	GAME_SIZE_MEDIUM,
	GAME_SIZE_BIG
};

public class Option {
	private String TAG = "Option";

	private long id;
	private int level;
	private int size;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return TAG + " { _id: " + String.valueOf(id)
			+ ", level: " + String.valueOf(level)
			+ ", size: " + String.valueOf(size)
			+ ", name: " + name + " }";
	}
}
