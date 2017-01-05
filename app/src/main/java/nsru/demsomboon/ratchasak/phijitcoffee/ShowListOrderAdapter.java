package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by KHAO on 19/10/2559.
 */
public class ShowListOrderAdapter extends BaseAdapter{


    //Explicit
    private Context context;
    private String[]dataString, coffeeString, amountString, statusString;
    private TextView dataTextview, coffeeTextview, amountTextview, statusTextview;

    public ShowListOrderAdapter(Context context, String[] dataString, String[] coffeeString, String[] amountString, String[] statusString) {
        this.context = context;
        this.dataString = dataString;
        this.coffeeString = coffeeString;
        this.amountString = amountString;
        this.statusString = statusString;
    }

    @Override
    public int getCount() {
        return coffeeString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.show_list_order_listview, viewGroup, false);
        //bind Widget
        dataTextview = (TextView) view1.findViewById(R.id.textView39);
        coffeeTextview  = (TextView) view1.findViewById(R.id.textView40);
        amountTextview = (TextView) view1.findViewById(R.id.textView41);
        statusTextview  = (TextView) view1.findViewById(R.id.textView42);
        //ShowView
        dataTextview.setText(dataString[i]);
        coffeeTextview.setText(coffeeString[i]);
        amountTextview.setText(amountString[i]);
        statusTextview.setText(statusString[i]);

        return view1;
    }
}//mainclass
