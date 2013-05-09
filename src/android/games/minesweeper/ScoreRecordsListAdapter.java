package android.games.minesweeper;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreRecordsListAdapter extends BaseAdapter {

	private static ArrayList<ListItemScoreRecords> itemDetailsrrayList;

	LayoutInflater layoutInflator;
	Context context;

	public ScoreRecordsListAdapter(ArrayList<ListItemScoreRecords> result , Context c) {
		// TODO Auto-generated constructor stub
		itemDetailsrrayList = result;
		context = c;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return itemDetailsrrayList.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemDetailsrrayList.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		layoutInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);                               
		View row = layoutInflator.inflate(R.layout.item_score_record, parent, false);

		TextView textview1 = (TextView)row.findViewById(R.id.textView1);
		TextView textview2 = (TextView)row.findViewById(R.id.textView2);
		ImageView imageview = (ImageView)row.findViewById(R.id.imageView1);

		textview1.setText(itemDetailsrrayList.get(position).getText1());
		textview2.setText(itemDetailsrrayList.get(position).getText2());
		imageview.setImageResource(itemDetailsrrayList.get(position).getImage());

		return (row);
	}
}