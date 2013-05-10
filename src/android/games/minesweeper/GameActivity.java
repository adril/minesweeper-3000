package android.games.minesweeper;

import java.util.Random;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends BaseActivity {
	
	private String TAG = this.toString();
	
	private float mx, my;
    private ScrollView vScroll;
    private HorizontalScrollView hScroll;
	
	private final static int BoxPadding = 8;
	private final static int BoxWH = 8;
	private TableLayout mineField;
	private int secondsPassed;
	private int minutesPassed;
	private int duration;
	private int score;
	private Handler timer;
	private TextView timerText;
	private TextView scoreText;
	private Option options;
	private Globals globals;
	public int totalRows;
	public int totalCols;
	public boolean gameLost;
	public UIBox[][] Boxes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		vScroll = (ScrollView) findViewById(R.id.vScroll);
        hScroll = (HorizontalScrollView) findViewById(R.id.hScroll);
		mineField = (TableLayout) findViewById(R.id.MineField);
		timerText = (TextView)findViewById(R.id.Timer);
		scoreText = (TextView)findViewById(R.id.Score);

		timer = new Handler();

		newGame();
	}

	public void newGame() {
		initializeGame();
		createGameBoard(options.getLevel());
		showGameBoard();
		startTimer();
		Log.d(TAG, "New game started with options: " + options.toString());
	}

	public void initializeGame() {
		// Get options from the database
		globals = (Globals)getApplication();
		options = globals.getOptionDataSource().getOption();

		// Set total rows and columns based on the difficulty
		totalRows = options.getSize() * 6 + 10;
		totalCols = options.getSize() * 6 + 10;
		
		duration = 0;
		secondsPassed = 0;
		minutesPassed = 0;
		score = 0;
		addScore(0);
		gameLost = false;
	}

	public void showGameBoard()
	{
		mineField.removeAllViews();
		//for every row
		for(int row=0;row<totalRows;row++)
		{
			//create a new table row
			TableRow tableRow = new TableRow(this);
			//set the height and width of the row
			tableRow.setLayoutParams(new TableRow.LayoutParams((BoxWH * BoxPadding) * totalCols, BoxWH * BoxPadding));
			
			//for every column
			for(int col=0;col<totalCols;col++)       
			{
				//set the width and height of the Box
				Boxes[row][col].setLayoutParams(new TableRow.LayoutParams(BoxWH * BoxPadding,  BoxWH * BoxPadding)); 
				//add some padding to the Box
				Boxes[row][col].setPadding(BoxPadding, BoxPadding, BoxPadding, BoxPadding);
				//add the Box to the table row
				tableRow.addView(Boxes[row][col]);
			}
			//add the row to the minefield layout
			mineField.addView(tableRow, new TableLayout.LayoutParams((BoxWH * BoxPadding) * totalCols, BoxWH * BoxPadding)); 
		}
	}

	public void createGameBoard(int diff)
	{
		//setup the Boxes array
		Boxes = new UIBox[totalRows][totalCols];

		for(int row = 0; row < totalRows;row++)
		{
			for(int col = 0; col < totalCols;col++)
			{
				final int curRow = row;
				final int curCol = col;

				//create a Box
				Boxes[row][col] = new UIBox(this);
				
				//set the Box defaults
				Boxes[row][col].setDefaults();
				Boxes[row][col].setRow(curRow);
				Boxes[row][col].setColumn(curCol);
				
				//set the Box properties
				int randDifficulty = (int)(new Random().nextInt() % (10 - options.getLevel()));
				if (randDifficulty == 0)
				{
					Boxes[row][col].plantMine();
					setupMineField(curRow, curCol);
				}

				//add a click listener
				Boxes[row][col].setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						Log.d(TAG, "Clicked on box (" + curCol + ", " + curRow + ")");
						UIBox box = (UIBox)view;
						box.openBox();
					}
				});

				//add a long click listener
				Boxes[row][col].setOnLongClickListener(new OnLongClickListener()
				{
					@Override
					public boolean onLongClick(View view)
					{
						Log.d(TAG, "Clicked on box (" + curCol + ", " + curRow + ")");
						UIBox box = (UIBox)view;
						box.toggleFlag();
						return true;
					}
				});
			}
		}
	}

	public void setupMineField(int row, int col)
	{
		//go one row and col back
		int startRow = row-1;
		int startCol = col-1;
		//check 3 rows across and 3 down
		int checkRows = 3;
		int checkCols = 3;
		if(startRow < 0) //if it is on the first row
		{
			startRow = 0;
			checkRows = 2;
		}
		else if(startRow+3 > totalRows) //if it is on the last row
			checkRows = 2;

		if(startCol < 0)
		{
			startCol = 0;
			checkCols = 2;
		}
		else if(startCol+3 > totalCols) //if it is on the last row
			checkCols = 2;

		for(int j=startRow;j<startRow+checkRows;j++) //3 rows across
		{
			for(int k=startCol;k<startCol+checkCols;k++) //3 rows down
			{
				if(Boxes != null && Boxes[j] != null && Boxes[j][k] != null && !Boxes[j][k].isMine()) //if it isn't a mine
					Boxes[j][k].updateSurroundingMineCount();
			}
		}
	}

	public void endGame()
	{
		//imageButton.setBackgroundResource(R.drawable.smile);

		// remove the table rows from the minefield table layout
		mineField.removeAllViews();

		// reset variables
		this.initializeGame();
	}

	public void startTimer()
	{
		if (secondsPassed == 0) {
			minutesPassed = 0;
			timer.removeCallbacks(updateTimer);
			timer.postDelayed(updateTimer, 1000);
		}
	}

	public void stopTimer()
	{
		timer.removeCallbacks(updateTimer);
	}

	private Runnable updateTimer = new Runnable()
	{
		public void run()
		{
			long currentMilliseconds = System.currentTimeMillis();
			++secondsPassed;
			++duration;
			String curSecs = Integer.toString(secondsPassed);
			if (secondsPassed < 10)
				curSecs = "0" + curSecs;
			else if (secondsPassed == 59) {
				secondsPassed = 0;
				minutesPassed++;
				addScore(-100);
			}
			String curMins = Integer.toString(minutesPassed);
			if (minutesPassed < 10)
				curMins = "0" + curMins;
			timerText.setText(curMins + ":" + curSecs);
			if (minutesPassed > 60)
				timerText.setText("infinity");
			
			scoreText.setText(new String(Integer.toString(score)));

			timer.postAtTime(this, currentMilliseconds);
			timer.postDelayed(updateTimer, 1000);
		}
	};
	
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return true;
    }

	public void loseGame()
	{
		gameLost = true;
		Log.d(TAG, "Lost the game.");
		stopTimer();

		for(int i=0;i<totalRows;i++)
		{
			for(int j=0;j<totalCols;j++)
			{
				//if the Box is covered
				if(Boxes[i][j].isCovered())
				{
					//if there is no flag or mine
					if(!Boxes[i][j].isFlag() && !Boxes[i][j].isMine())
					{
						Boxes[i][j].openBox();
					}
					//if there is a mine but no flag
					else if(Boxes[i][j].isMine() && !Boxes[i][j].isFlag())
					{
						Boxes[i][j].openBox();
					}
					//if there is a mine and flag
					else if(Boxes[i][j].isMine() && Boxes[i][j].isFlag())
					{
						addScore(Boxes[i][j].getNoSurroundingMines() * 20);
						Boxes[i][j].openBox();
					}

				}
			}
		}
		finalizeScore();
	}
	
	public void finalizeScore() {
		scoreText.setText(new String(Integer.toString(score)));
		this.globals.getScoreDataSource().createScore(score, options.getName(), options.getSize(), options.getLevel(), duration);
	}
	
	public void addScore(int val) {
		if (!gameLost)
			score += val;
		if (score < 0)
			score = 0;
	}

    public boolean onCreateOptionsMenu(Menu menu) {
 
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.new_game:
    		Toast.makeText(this, "New game!", Toast.LENGTH_SHORT).show();
    		newGame();
    		break;

    	default:
    		break;
    	}
    	return true;
    }
}
