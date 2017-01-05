package nsru.demsomboon.ratchasak.phijitcoffee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by KHAO on 12/6/2559.
 */
public class promoteAdapter extends BaseAdapter{
    //Explicit
    private Context context;
    private String[] strings;
    public promoteAdapter(Context context,String[]strings){
        this.context = context;
        this.strings = strings;



    }


    @Override
    public int getCount() {
        return strings.length;
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
        ImageView imageView =(ImageView) view1.findViewById(R.id.imageView3);
        Picasso.with(context).load(strings[i]).resize(300,18).into(imageView);

        return view1;
    }
}//Main Class
