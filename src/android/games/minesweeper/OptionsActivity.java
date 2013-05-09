package android.games.minesweeper;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OptionsActivity extends Activity {


	RadioGroup	rg_lvl;
	RadioButton rb_selected_lvl;
	RadioGroup	rg_size;
	RadioButton rb_selected_size;
	EditText	set_player_name;
	Button		remove;
	String		player_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		rg_lvl = (RadioGroup) super.findViewById(R.id.rg_lvl);
		rg_size = (RadioGroup) super.findViewById(R.id.rg_size);
		set_player_name = (EditText) super.findViewById(R.id.e_name);
		remove = (Button) super.findViewById(R.id.b_remove);
		
		rg_lvl.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selected_lvl = rg_lvl.getCheckedRadioButtonId();
				rb_selected_lvl = (RadioButton) findViewById(selected_lvl);
				//Toast.makeText(getBaseContext(), (String) rb_selected_lvl.getText(), Toast.LENGTH_SHORT).show();
				Log.d("Checked SELECTED IS : ", (String) rb_selected_lvl.getText());

			}
		});

		rg_size.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selected_size = rg_size.getCheckedRadioButtonId();
				rb_selected_size = (RadioButton) findViewById(selected_size);
				//Toast.makeText(getBaseContext(), (String) rb_selected_size.getText(), Toast.LENGTH_SHORT).show();
				Log.d("Checked SELECTED IS : ", (String) rb_selected_size.getText());

			}
		});

		set_player_name.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 if((event.getAction()==KeyEvent.ACTION_DOWN)&&
		                    (keyCode==KeyEvent.KEYCODE_ENTER)){
		                player_name = set_player_name.getText().toString();
		                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		                imm.hideSoftInputFromWindow(set_player_name.getWindowToken(),0);
		                Log.d("Edit TExt PLAYER NAME : ", player_name);
		                return true;
		            }
				return false;
			}
		});
		
		remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO remove all record from the DATA BASE
				((Globals)getApplication()).getScoreDataSource().deleteAllScores();
				Log.d("Button Remove : ", "ok");
			}
		});
		
	}


}
