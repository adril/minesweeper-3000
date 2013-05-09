package android.games.minesweeper;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {
	private ScoreDataSource scoreDataSource;
	private OptionDataSource optionDataSource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		((Globals)getApplication()).initialize();

		// SCORE DB
	    scoreDataSource = ((Globals)getApplication()).getScoreDataSource();
		scoreDataSource.createScore(12000, "test", 12, 1, 33);
		List<Score> scores = scoreDataSource.getAllScores();
		for (int i = 0; i < scores.size(); i++)
		{
			Log.d("Scores", scores.get(i).toString());
		}
		scoreDataSource.close();
		
		// OPTIONS DB
	    optionDataSource = ((Globals)getApplication()).getOptionDataSource();
	    optionDataSource.createOption("Easy", game_size.GAME_SIZE_BIG.ordinal(), level.LEVEL_EASY.ordinal());
	    optionDataSource.deleteAllOptions();
	    optionDataSource.createOption("Easy", game_size.GAME_SIZE_BIG.ordinal(), level.LEVEL_EASY.ordinal());
	    Option optTmp = optionDataSource.getOption();
	    optionDataSource.deleteOption(optTmp);
	    optionDataSource.createOption("Easy", game_size.GAME_SIZE_MEDIUM.ordinal(), level.LEVEL_MEDIUM.ordinal());
		List<Option> options = optionDataSource.getAllOptions();
		for (int i = 0; i < options.size(); i++)
		{
			options.get(i).setName(options.get(i).getName() + " modified");
			Log.d("Options", options.get(i).toString());
		}
		optionDataSource.close();
        
        View newGameButton = findViewById(R.id.new_game_button);
        newGameButton.setOnClickListener(this);
        
        View continueButton = findViewById(R.id.continue_game_button);
        continueButton.setOnClickListener(this);
        
        View highscoresButton = findViewById(R.id.high_scores_button);
        highscoresButton.setOnClickListener(this);
        
        View rulesButton = findViewById(R.id.rules_button);
        rulesButton.setOnClickListener(this);
        
        View exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
	      switch(v.getId())
	      {
	      	case R.id.high_scores_button:
	            Intent ihs = new Intent(this, HighScoresActivity.class);
	            startActivity(ihs);
	      		break;
	      	case R.id.continue_game_button:
	            openNewGameDialog();
	      		break;
	        case R.id.new_game_button:
	            openNewGameDialog();
	            break;
	        case R.id.rules_button:
	            Intent ir = new Intent(this, Rules.class);
	            startActivity(ir);
	            break;
	        case R.id.exit_button:
	          finish(); //end the application
	          break;
	      }
	}
	
	private void openNewGameDialog()
	{
	      new AlertDialog.Builder(this)
	    .setTitle(R.string.difficulty_title)
	    .setItems(R.array.difficulty, 
	        new DialogInterface.OnClickListener()
	        {
	          public void onClick(DialogInterface dialoginterface, int i)
	          {
	            startNewGame(i);
	          }
	        }).show();
	 }
	
	private void startNewGame(int i)
	{
	      Intent intent = new Intent(this, GameActivity.class);
	      //intent.putExtra(GameActivity.KEY_DIFFICULTY, i);
	      startActivity(intent);
	}

}
