package android.games.minesweeper;

import android.games.minesweeper.ScoreDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private List<Score> Scores;
	private Map<String, List<Score>> groupedScores;
	private ListItemScoreRecords item_details;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_score_record);
		super.onCreate(savedInstanceState);
		
		dataSource = ((Globals)getApplication()).getScoreDataSource();
		Scores = dataSource.getAllScores();
		groupedScores = dataSource.getGroupedScores();
		groupedScores.clear();
				
		text = getScoreRecordsText();

		for (int i = 0; i < Scores.size(); i++) {
			Log.d(TAG, text.get(i).text1);
		}

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
		int score;
		ScoreRecordsText scoreRecordsText;

		for (int i = 0; i < Scores.size(); i++) {
			score = Scores.get(i).getScore();
			scoreRecordsText = text.get(i);
			item_details = new ListItemScoreRecords();
			Log.d(TAG, "GetScoreRecordList text1: " + scoreRecordsText.text1 + " text2:  " + scoreRecordsText.text2);
			item_details.setText(scoreRecordsText.text1, scoreRecordsText.text2);
			if (score < 400)
				item_details.setImage(image[0]);
			else if (score >= 400 && score <= 2000)
				item_details.setImage(image[1]);
			else if (score > 3000)
				item_details.setImage(image[2]);
			results.add(item_details);
		}
		return results;
	}

	private ArrayList<ScoreRecordsText> getScoreRecordsText() {
		ArrayList<ScoreRecordsText> results = new ArrayList<ScoreRecordsText>();
		ScoreRecordsText score_records_text = new ScoreRecordsText();
		Score score;
		
		for (int i = 0; i < Scores.size(); i++) {
			score = Scores.get(i);
			score_records_text.text1 = "Score: " + score.getScore() + " with size " + Globals.gameSizeToString(score.getSize());
			score_records_text.text2 = score.getName() + " has finished the game with difficulty ";
			score_records_text.text2 += Globals.levelToString(score.getLevel()) + " in " + Scores.get(i).getDuration() + " seconds.";
			Log.d(TAG, "getScoreRecordsText: text: " + score_records_text.text1 + score_records_text.text2);
			results.add(score_records_text);
		}
		return results;
	}
}
