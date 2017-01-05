package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by KHAO on 1/5/2559.
 */
public class OrderAdapter extends BaseAdapter{


    //Explicit
    private Context context;
    private String[] nameStrings, priceStrings, descripStrings, iconStrings;

    public OrderAdapter(Context context,
                        String[] nameStrings,
                        String[] priceStrings,
                        String[] descripStrings,
                        String[] iconStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.priceStrings = priceStrings;
        this.descripStrings = descripStrings;
        this.iconStrings = iconStrings;
    }//Constructor

    @Override
    public int getCount() {
        return nameStrings.length;
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
        View view1 = layoutInflater.inflate(R.layout.order_listview, viewGroup, false);

        //For TextView
        TextView nameTextView = (TextView) view1.findViewById(R.id.textView14);
        nameTextView.setText(nameStrings[i]);

        TextView priceTextView = (TextView) view1.findViewById(R.id.textView15);
        priceTextView.setText(priceStrings[i]);
        TextView descripTextView = (TextView) view1.findViewById(R.id.textView13);
        descripTextView.setText(descripStrings[i]);
        //For Image
        ImageView iconImageView = (ImageView) view1.findViewById(R.id.imageView2);
        Picasso.with(context).load(iconStrings[i]).resize(150,100).into(iconImageView);
        return view1;
    }
}
