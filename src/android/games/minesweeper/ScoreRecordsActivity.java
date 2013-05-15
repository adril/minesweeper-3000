package android.games.minesweeper;

import android.games.minesweeper.ScoreDataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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

	private ArrayList<ScoreRecordsText> text;

	private int[] image = { R.drawable.icon_good, R.drawable.icon_normal, R.drawable.icon_bad };

	private ScoreDataSource dataSource;
	private List<Score> Scores;
	private Map<String, List<Score>> groupedScores;
	private ListItemScoreRecords item_details;
	private ArrayList<ListItemScoreRecords> listItemEasy;
	private ArrayList<ListItemScoreRecords> listItemMedium;
	private ArrayList<ListItemScoreRecords> listItemHard;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_score_record);
		super.onCreate(savedInstanceState);
		
		dataSource = ((Globals)getApplication()).getScoreDataSource();
		Scores = dataSource.getAllScores();
		groupedScores = dataSource.getGroupedScores();
		//groupedScores.clear();

		final ListView listViewHard = (ListView)findViewById(R.id.score_list_view_hard);
		final ListView listViewMedium = (ListView)findViewById(R.id.score_list_view_medium);
		final ListView listViewEasy = (ListView)findViewById(R.id.score_list_view_easy);
		
		Set<Map.Entry<String, List<Score>>> s = groupedScores.entrySet();
		Iterator<Entry<String, List<Score>>> it = s.iterator();
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
        	Entry<String, List<Score>> m =(Entry<String, List<Score>>)it.next();

            // getKey is used to get key of Map
            String key=(String)m.getKey();
            
            // getValue is used to get value of key in Map
            if (key == "unknown") {
                Log.d(TAG, "GroupedScoresMap(Easy) ");
                List<Score> value= new ArrayList<Score>(m.getValue());
                text = new ArrayList<ScoreRecordsText>(getScoreRecordsText(value));
                ArrayList<ListItemScoreRecords> listItemArray = getScoreRecordsList(value);
               
            }
            else if (key == "medium") {
                Log.d(TAG, "GroupedScoresMap(Medium)");
                List<Score> value= new ArrayList<Score>(m.getValue());
                text = new ArrayList<ScoreRecordsText>(getScoreRecordsText(value));
                ArrayList<ListItemScoreRecords> listItemArrayMedium = new ArrayList<ListItemScoreRecords>(getScoreRecordsList(value));
        		listViewMedium.setAdapter(new ScoreRecordsListAdapter(listItemArrayMedium, getApplicationContext()));
            }
            else if (key == "hard"){
                Log.d(TAG, "GroupedScoresMap(hard)");
                List<Score> value= new ArrayList<Score>(m.getValue());
                text = new ArrayList<ScoreRecordsText>(getScoreRecordsText(value));
                ArrayList<ListItemScoreRecords> listItemArrayHard = new ArrayList<ListItemScoreRecords>(getScoreRecordsList(value));
        		listViewHard.setAdapter(new ScoreRecordsListAdapter(listItemArrayHard, getApplicationContext()));
            }
           // Log.d(TAG, "GroupedScoresMap(Key :"+key+" Value :"+value+")");
            Log.d(TAG, "---------------------------------------");
        }
		
		listViewHard.setOnItemClickListener(new OnItemClickListener() {

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
		listViewMedium.setOnItemClickListener(new OnItemClickListener() {

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
		listViewEasy.setOnItemClickListener(new OnItemClickListener() {

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

	private ArrayList<ListItemScoreRecords> getScoreRecordsList(List<Score> score_list) {
		ArrayList<ListItemScoreRecords> results = new ArrayList<ListItemScoreRecords>();
		int score;
		ScoreRecordsText scoreRecordsText;

		for (int i = 0; i < score_list.size(); i++) {
			score = score_list.get(i).getScore();
			scoreRecordsText = text.get(i);
			item_details = new ListItemScoreRecords();
			Log.d(TAG, "GetScoreRecordList text1: " + scoreRecordsText.text1 + " text2:  " + scoreRecordsText.text2);
			item_details.setText(scoreRecordsText.text1, scoreRecordsText.text2);
			if (score < 400)
				item_details.setImage(image[2]);
			else if (score >= 400 && score <= 2000)
				item_details.setImage(image[1]);
			else if (score > 3000)
				item_details.setImage(image[0]);
			results.add(item_details);
		}
		return results;
	}

	private ArrayList<ScoreRecordsText> getScoreRecordsText(List<Score> score_list) {
		ArrayList<ScoreRecordsText> results = new ArrayList<ScoreRecordsText>();
		for (int i = 0; i < score_list.size(); i++) {
			Score score;
			ScoreRecordsText score_records_text = new ScoreRecordsText();
			score = score_list.get(i);
			score_records_text.text1 = "Score: " + score.getScore() + " in " + Globals.gameSizeToString(score.getSize()) + " size";
			score_records_text.text2 = score.getName() + " has finished the game";
			score_records_text.text2 +=  " in " + Scores.get(i).getDuration() + " seconds.";
			Log.d(TAG, "getScoreRecordsText: text: " + score_records_text.text1 + score_records_text.text2);
			results.add(score_records_text);
		}
		return results;
	}
}
