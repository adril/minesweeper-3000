package android.games.minesweeper;

public class ListItemScoreRecords {

	private String text1;
	private String text2;
	private int image;

	public String getText1() {
		return text1;
	}
	
	public String getText2() {
		return text2;
	}
	
	public void setText(String text1, String text2) {
		this.text1 = text1;
		this.text2 = text2;
	}
	
	public int getImage() {
		return image;
	}
	
	public void setImage(int image) {
		this.image = image;
	}
}
