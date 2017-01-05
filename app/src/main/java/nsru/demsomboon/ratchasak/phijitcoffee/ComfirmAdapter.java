package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by KHAO on 12/6/2559.
 */
public class ComfirmAdapter extends BaseAdapter{
    //Explicit
    private Context context;
    private String[] coffeeStrings, priceStrings, amountStrings;

    public ComfirmAdapter(Context context,
                          String[] coffeeStrings,
                          String[] priceStrings,
                          String[] amountStrings) {
        this.context = context;
        this.coffeeStrings = coffeeStrings;
        this.priceStrings = priceStrings;
        this.amountStrings = amountStrings;
    }//Constructor

    @Override
    public int getCount() {
        return coffeeStrings.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.comfirm_listview, viewGroup, false);

        TextView coffeeTextView = (TextView) view1.findViewById(R.id.textView28);
        coffeeTextView.setText(coffeeStrings[i]);
        TextView priceTextView = (TextView) view1.findViewById(R.id.textView29);
        priceTextView.setText(priceStrings[i]);
        TextView amountTextView = (TextView) view1.findViewById(R.id.textView30);
        amountTextView.setText(amountStrings[i]);






        return view1;
    }
}//Main class
