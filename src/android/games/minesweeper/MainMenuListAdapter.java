package android.games.minesweeper;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenuListAdapter extends BaseAdapter {

	private static ArrayList<ListItemMainMenuDetails> itemDetailsrrayList;

	LayoutInflater layoutInflator;
	Context context;

	public MainMenuListAdapter(ArrayList<ListItemMainMenuDetails> result , Context c) {
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
		View row = layoutInflator.inflate(R.layout.item_main_menu, parent, false);

		TextView textview = (TextView)row.findViewById(R.id.textView1);
		ImageView imageview = (ImageView)row.findViewById(R.id.imageView1);

		textview.setText(itemDetailsrrayList.get(position).getName());
		imageview.setImageResource(itemDetailsrrayList.get(position).getImage());

		return (row);
	}
}