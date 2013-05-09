package android.games.minesweeper;

import android.games.minesweeper.ScoreDataSource;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ScoreRecordsActivity extends BaseActivity implements OnClickListener {

	class ScoreRecordsText { public String text1, text2; }

	private String TAG = "ScoreActivity";

	private ArrayList<ScoreRecordsText> text = new ArrayList<ScoreRecordsText>();

	private int[] image = { R.drawable.icon_good, R.drawable.icon_normal, R.drawable.icon_bad };

	private ScoreDataSource dataSource;
	private List<Score> Scores = new ArrayList<Score>();
	private ListItemScoreRecords item_details;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_score_record);
		super.onCreate(savedInstanceState);
		
		dataSource = new ScoreDataSource(this);
		dataSource.open();
		Scores = dataSource.getAllScores();
		text = getScoreRecordsText();

		final ListView listView = (ListView)findViewById(R.id.score_list_view);
		ArrayList<ListItemScoreRecords> listItemArray = getScoreRecordsList();
		//if (listItemArray.isEmpty() == false)
		
		
		
		listView.setAdapter(new ScoreRecordsListAdapter(listItemArray, getApplicationContext()));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d(TAG, "Selected item:" + arg2 + " - " + arg3);
				switch (arg2) {
				case 0:
					// Details of his game
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private ArrayList<ListItemScoreRecords> getScoreRecordsList() {

		ArrayList<ListItemScoreRecords> results = new ArrayList<ListItemScoreRecords>();

		for (int i = 0; i < Scores.size(); i++) {
			item_details = new ListItemScoreRecords();
			Log.d(TAG, "GetScoreRecordList text1: " + text.get(i).text1 + " text2:  " + text.get(i).text2);
			item_details.setText(text.get(i).text1, text.get(i).text2);
			if (Scores.get(i).getScore() < 40)
				item_details.setImage(image[0]);
			else if (Scores.get(i).getScore()/1000 >= 40 && Scores.get(i).getScore()/1000 <= 70)
				item_details.setImage(image[1]);
			else if (Scores.get(i).getScore()/1000 > 70)
				item_details.setImage(image[2]);
			results.add(item_details);
		}

		return results;
	}

	private ArrayList<ScoreRecordsText> getScoreRecordsText() {

		ArrayList<ScoreRecordsText> results = new ArrayList<ScoreRecordsText>();
		ScoreRecordsText score_records_text = new ScoreRecordsText();
		for (int i = 0; i < Scores.size(); i++) {
			score_records_text.text1 = Scores.get(i).getScore()/1000 + "% Completed with size " + Scores.get(i).getSize();
			score_records_text.text2 = Scores.get(i).getName() + " finish the game with the difficulty " + Scores.get(i).getLevel() + " in " + Scores.get(i).getDuration() + " seconds.";
			results.add(score_records_text);
		}

		return results;
	}
}
