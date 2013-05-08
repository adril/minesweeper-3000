package android.games.minesweeper;

public class Score {
  private long id;
  private long score;
  private String name;
  private long size;
  private String date;
  private long duration;
  private long level;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public long getLevel() {
    return level;
  }

  public void setLevel(long level) {
    this.level = level;
  }

  @Override
  public String toString() {
    return "Score number " + String.valueOf(id)
    		+ ": score:" + String.valueOf(score)
    		+ ", duration:" + String.valueOf(duration)
    		+ ", level:" + String.valueOf(level)
    		+ ", size:" + String.valueOf(size)
    		+ ", date:" + date
    		+ ", name:" + name;
  }
}
