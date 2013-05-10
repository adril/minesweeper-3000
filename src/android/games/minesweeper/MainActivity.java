package android.games.minesweeper;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.games.minesweeper.R;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends BaseActivity {
	private String TAG = "MainActivity";
	private String[] text = { "Play", "Score Records", "Options", "Help" };
	private int[] image = { R.drawable.play_icon, R.drawable.heart_icon, R.drawable.cup_icon, R.drawable.skull_icon };
	private ListItemMainMenu item_details;
	private Globals globals;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.initializeLayout();
		this.initializeDatabase();
	}

	private void initializeLayout() {
		ArrayList<ListItemMainMenu> listItemArray = getMainMenuDetailsList();
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

	private void initializeDatabase() {
		// Instantiates ScoreDataSource and OptionDataSource in the Globals variable
		// The Globals variable declares variables that can be accessed anywhere in the code.
		// Access with: (Globals)getApplication()
		globals = (Globals)getApplication();
		globals.initializeDatabase();
		globals.seedDatabase();
	}

	private ArrayList<ListItemMainMenu> getMainMenuDetailsList() {
		ArrayList<ListItemMainMenu> results = new ArrayList<ListItemMainMenu>();

		for (int i = 0; i < text.length; i++) {
			item_details = new ListItemMainMenu();
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
		globals.closeDatabase();
		super.onDestroy();
	}
}
