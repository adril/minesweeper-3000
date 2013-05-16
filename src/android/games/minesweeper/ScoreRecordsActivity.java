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
 
        class ScoreRecordsText { public String text1, text2; int img; }
 
        private String TAG = "ScoreActivity";
 
        private ArrayList<ScoreRecordsText> text;
 
        private int[] image = { R.drawable.icon_good, R.drawable.icon_normal, R.drawable.icon_bad };
 
        private ScoreDataSource dataSource;
        private List<Score> Scores;
 
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                setContentView(R.layout.activity_score_record);
                super.onCreate(savedInstanceState);
               
                dataSource = ((Globals)getApplication()).getScoreDataSource();
                Scores = dataSource.getAllScores();
 
                final ListView listView = (ListView)findViewById(R.id.score_list_view);
                text = new ArrayList<ScoreRecordsText>(getScoreRecordsText(Scores));
                ArrayList<ListItemScoreRecords> listItemArray = new ArrayList<ListItemScoreRecords>();
                listItemArray.addAll(getScoreRecordsList(Scores));
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
 
        private ArrayList<ListItemScoreRecords> getScoreRecordsList(List<Score> score_list) {
                ArrayList<ListItemScoreRecords> results = new ArrayList<ListItemScoreRecords>();
                int score;
 
                for (int i = 0; i < score_list.size(); i++) {
                        score = score_list.get(i).getScore();
                        ScoreRecordsText scoreRecordsText = new ScoreRecordsText();
                        scoreRecordsText = text.get(i);
                        ListItemScoreRecords item_details = new ListItemScoreRecords();
                        Log.d(TAG, "GetScoreRecordList text1: " + scoreRecordsText.text1 + " text2:  " + scoreRecordsText.text2);
                        item_details.setText(scoreRecordsText.text1, scoreRecordsText.text2);
                        item_details.setImage(image[scoreRecordsText.img]);
                        results.add(item_details);
                }
                return results;
        }
 
        private ArrayList<ScoreRecordsText> getScoreRecordsText(List<Score> score_list) {
                ArrayList<ScoreRecordsText> results = new ArrayList<ScoreRecordsText>();
                String level = "NULL";
                for (int i = score_list.size()-1; i >= 0 ; i--) {
                        Score score;
                        ScoreRecordsText score_records_text = new ScoreRecordsText();
                        score = score_list.get(i);
                        switch(score.getLevel()) {
                        case 0:
                                level = "Easy";
                                break;
                        case 1:
                                level = "Medium";
                                break;
                        case 2:
                                level = "Hard";
                                break;
                        }
                        if (score.getScore() < 40)
                        	score_records_text.img = 2;
                        else if (score.getScore() >= 40 && score.getScore() <= 70)
                        	score_records_text.img = 1;
                        else if (score.getScore() > 70)
                        	score_records_text.img = 0;
                        score_records_text.text1 = "Level : " + level + " -- Score: " + score.getScore() + " in " + Globals.gameSizeToString(score.getSize()) + " size";
                        score_records_text.text2 = score.getName() + " has finished the game";
                        score_records_text.text2 +=  " in " + Scores.get(i).getDuration() + " seconds.";
                        Log.d(TAG, "getScoreRecordsText: text: " + score_records_text.text1 + score_records_text.text2);
                        results.add(score_records_text);
                }
                return results;
        }
}