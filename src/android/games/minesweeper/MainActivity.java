package android.games.minesweeper;

import java.util.ArrayList;

import java.util.List;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private String TAG = "MainActivity";

	private String[] text = { "Play", "Score Records", "Options", "Help" };

	private int[] image = { R.drawable.play_icon, R.drawable.heart_icon, R.drawable.cup_icon, R.drawable.skull_icon };

	private ListItemMainMenuDetails item_details;
	private ScoreDataSource scoreDataSource; // For db example. See example in onCreate function.
	private OptionDataSource optionDataSource; // For db example. See example in onCreate function.

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Instantiates ScoreDataSource and OptionDataSource in the Globals variable
		// The Globals variable declares variables that can be accessed anywhere in the code.
		// Access with: (Globals)getApplication() 
		((Globals)getApplication()).initialize();
		
		// SCORE DB EXAMPLE
		// From anywhere in the application, access ScoreDataSource (which
		// give access to the scores records in the database) using:
		scoreDataSource = ((Globals)getApplication()).getScoreDataSource();
		// then manipulate your data:
		scoreDataSource.createScore(12000, "test", game_size.GAME_SIZE_BIG.ordinal(),
				level.LEVEL_HARD.ordinal(), 33);
		scoreDataSource.deleteAllScores();
		List<Score> scores = scoreDataSource.getAllScores();
		for (int i = 0; i < scores.size(); i++)
		{
			Log.d("Database", scores.get(i).toString());
		}

		// OPTIONS DB EXAMPLE
		// From anywhere in the application, access OptionDataSource (which
		// give access to the options records in the database) using:
		optionDataSource = ((Globals)getApplication()).getOptionDataSource();
		// then manipulate your data:
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

		//		ActionBar actionBar = getActionBar();	   
		//		actionBar.setDisplayUseLogoEnabled(false);

		//INFO: init List View with Static Data
		ArrayList<ListItemMainMenuDetails> listItemArray = getMainMenuDetailsList();
		final ListView listView = (ListView)findViewById(R.id.main_list_view);
		listView.setAdapter(new MainMenuListAdapter(listItemArray, getApplicationContext()));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d(TAG, "Selected item:" + arg2 + " - " + arg3);
				switch (arg2) {
				case 0:
					Intent ig = new Intent(MainActivity.this, GameActivity.class);
					startActivity(ig);
					break;
				case 1:
					Intent isr = new Intent(MainActivity.this, ScoreRecordsActivity.class);
					startActivity(isr);
					break;
				case 2:
					Intent io = new Intent(MainActivity.this, OptionsActivity.class);
					startActivity(io);

					break;
				case 3:
					Intent ih = new Intent(MainActivity.this, HelpActivity.class);
					startActivity(ih);
					break;

				default:
					break;
				}
			}                 
		});
	}

	private ArrayList<ListItemMainMenuDetails> getMainMenuDetailsList() {

		ArrayList<ListItemMainMenuDetails> results = new ArrayList<ListItemMainMenuDetails>();

		for (int i = 0; i < text.length; i++) {
			item_details = new ListItemMainMenuDetails();
			item_details.setName(text[i]);
			item_details.setImage(image[i]);
			results.add(item_details);
		}

		return results;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
}
