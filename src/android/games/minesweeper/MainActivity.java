package android.games.minesweeper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
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
		// TODO Auto-generated method stub

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
