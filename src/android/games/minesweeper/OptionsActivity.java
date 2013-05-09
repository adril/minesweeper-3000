package android.games.minesweeper;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.StaticLayout;
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


	String TAG = "OptionsActivity";
	
	RadioGroup	rg_lvl;
	RadioButton rb_selected_lvl;
	RadioGroup	rg_size;
	RadioButton rb_selected_size;
	EditText	txt_player_name;
	Button		bt_remove;

	Option optionRecord;

	private void setupView() {
		rg_lvl = (RadioGroup)super.findViewById(R.id.rg_lvl);
		rg_size = (RadioGroup)super.findViewById(R.id.rg_size);
		txt_player_name = (EditText)super.findViewById(R.id.e_name);
		bt_remove = (Button)super.findViewById(R.id.b_remove);
	}

	private void setupListner() {
		rg_lvl.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selected_lvl = rg_lvl.getCheckedRadioButtonId();

				rb_selected_lvl = (RadioButton) findViewById(selected_lvl);
				Log.d("Checked SELECTED IS : ", (String) rb_selected_lvl.getText() + " | " + selected_lvl);

				switch (selected_lvl) {
				case R.id.lvl_easy:
					optionRecord.setLevel(0);
					break;
				case R.id.lvl_medium:
					optionRecord.setLevel(1);
					break;
				case R.id.lvl_hard:
					optionRecord.setLevel(2);
					break;

				default:
					break;
				}
			}
		});

		rg_size.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selected_size = rg_size.getCheckedRadioButtonId();

				rb_selected_size = (RadioButton) findViewById(selected_size);
				Log.d("Checked SELECTED IS : ", (String) rb_selected_size.getText());

				switch (selected_size) {
				case R.id.size_small:
					optionRecord.setSize(0);
					break;
				case R.id.size_medium:
					optionRecord.setSize(1);
					break;
				case R.id.size_big:
					optionRecord.setSize(2);
					break;

				default:
					break;
				}
			}
		});

		txt_player_name.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					String player_name = txt_player_name.getText().toString();
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txt_player_name.getWindowToken(), 0);

					//INFO: setting name
					optionRecord.setName(player_name);

					Log.d("Edit Text PLAYER NAME : ", player_name);
					return true;
				}
				return false;
			}
		});

		bt_remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO remove all record from the DATA BASE
				Log.d("Button Remove : ", "ok");
			}
		});
	}

	private int buttonIdForLevel(final int level) {
		int buttonId = 0;

		switch (level) {
		case 0:
			buttonId = R.id.lvl_easy;	
			break;
		case 1:
			buttonId = R.id.lvl_medium;
			break;
		case 2:
			buttonId = R.id.lvl_hard;
			break;

		default:
			break;
		}
		return buttonId;
	}

	private int buttonIdForSize(final int size) {
		int buttonId = 0;

		switch (size) {
		case 0:
			buttonId = R.id.size_small;
			break;
		case 1:
			buttonId = R.id.size_medium;
			break;
		case 2:
			buttonId = R.id.size_big;
			break;

		default:
			break;
		}
		return buttonId;
	}

	private void setupOption() {
		//INFO: debug
		optionRecord = new Option();
		optionRecord.setId(0);
		optionRecord.setLevel(1);
		optionRecord.setName("Adril");
		optionRecord.setSize(1);

		int lvlButtonId = buttonIdForLevel(1);//(optionRecord.getLevel());
		rg_lvl.check(lvlButtonId);
		int sizeButtonId = buttonIdForSize(1);//(optionRecord.getSize());
		rg_size.check(sizeButtonId);
		txt_player_name.setText(optionRecord.getName());

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

		//TODO: set Option with model
		// Options options = Global.getOption(); 
		//setControl with options
		//edition -> modification de Options
		//Quit view -> save option

		setupView();
		setupListner();		
		setupOption();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "on DESTROY");
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		Log.d(TAG, "on STOP");
		//TODO: save db

	}
}
