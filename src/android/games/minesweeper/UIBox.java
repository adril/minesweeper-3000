package android.games.minesweeper;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class UIBox extends Button
{
	
	private String TAG = "UIBox";
	
	private boolean isMine;
	private boolean isFlag;
	private boolean isQuestionMark;
	private boolean isCovered;
	private int noSurroundingMines;
	private GameActivity gameActivity;
	private int column;
	private int row;

	public UIBox(Context context)
	{
		super(context);
		this.gameActivity = (GameActivity)context;
		this.setTextColor(Color.RED);
	}

	public UIBox(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public UIBox(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public void setDefaults()
	{
		isMine = false;
		isFlag = false;
		isQuestionMark = false;
		isCovered = true;
		noSurroundingMines = 0;

		this.setBackgroundResource(R.drawable.empty);
	}
	
	public void toggleFlag() {
		this.setBackgroundResource(R.drawable.flag);
		this.isFlag = !this.isFlag;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setMine(boolean mine)
	{
		isMine = mine;
	}

	public void setFlag(boolean flag)
	{
		isFlag = flag;
	}

	public void setQuestionMark(boolean flag)
	{
		isQuestionMark = flag;
	}

	public void setUncovered()
	{
		isCovered = false;
		uncoverNeighbors();
	}

	public void setSurroundingNumber(int number)
	{
		noSurroundingMines = number;
	}

	//uncover the Box
	public void openBox()
	{
		if(!isCovered)
			return;

		setUncovered();
		if(this.isMine())
			triggerMine();
		else
			showNumber();
		gameActivity.addScore(10*noSurroundingMines);
	}

	private void uncoverNeighbors() {
		//if the Box is a mine, or a flag return
		if(gameActivity.Boxes[row][column].isMine() || gameActivity.Boxes[row][column].isFlag())
			return;

		gameActivity.Boxes[row][column].openBox();

		if (gameActivity.Boxes[row][column].getNoSurroundingMines() > 0)
			return;

		//the box on the top
		int startRow = row-1;
		int startCol = column-1;

		int checkRows = 3;
		int checkCols = 3;
		if (startRow < 0) //if it is on the first row
		{
			startRow = 0;
			checkRows = 2;
		}
		else if (startRow+3 > gameActivity.totalRows) //if it is on the last row
			checkRows = 2;

		if (startCol < 0)
		{
			startCol = 0;
			checkCols = 2;
		}
		else if (startCol+3 > gameActivity.totalCols) //if it is on the last row
			checkCols = 2;

		for (int i=startRow;i<startRow+checkRows;i++) //3 or 2 rows across
		{
			for (int j=startCol;j<startCol+checkCols;j++) //3 or 2 rows down
			{
				if (gameActivity.Boxes[i][j].isCovered()
					&& gameActivity.Boxes[i][j].isMine() == false
					&& gameActivity.Boxes[i][j].noSurroundingMines == 0)
					gameActivity.Boxes[i][j].openBox();
			}
		}
	}

	//show the number icon
	public void showNumber()
	{
		gameActivity.addScore(12*(noSurroundingMines+1));
		uncoverNeighbors();
		this.setBackgroundResource(R.drawable.emptyauto);
		
		String bombToward = "" + noSurroundingMines;
		Log.d(TAG, bombToward);
		this.setText(bombToward);

	}

	//show the mine icon
	public void triggerMine()
	{
		if (gameActivity.gameLost == false)
			gameActivity.loseGame();
		this.setBackgroundResource(R.drawable.bomb);
	}

	//set the Box as a mine
	public void plantMine()
	{
		isMine = true;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}

	public boolean isMine()
	{
		return isMine;
	}

	public boolean isFlag()
	{
		return isFlag;
	}

	public boolean isCovered()
	{
		return isCovered;
	}

	public boolean isQuestionMark()
	{
		return isQuestionMark;
	}

	public int getNoSurroundingMines()
	{
		return noSurroundingMines;
	}

	public void updateSurroundingMineCount()
	{
		noSurroundingMines++;
	}
}