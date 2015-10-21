package baac.it.natoshi.baacrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by BAAC on 21/10/2558.
 */
public class myAdapter extends BaseAdapter{

    // Exp
    private Context objContext;
    private String[] sourceStrings,foodStrings, priceString;

    public myAdapter(Context objContext, String[] sourceStrings, String[] foodStrings, String[] priceString) {
        this.objContext = objContext;
        this.sourceStrings = sourceStrings;
        this.foodStrings = foodStrings;
        this.priceString = priceString;
    }

    @Override
    public int getCount() {
        return foodStrings.length;
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
        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView1 = objLayoutInflater.inflate(R.layout.food_listview,viewGroup,false);

        // For Show Food
        TextView foodTextView = (TextView) objView1.findViewById(R.id.txtShowFood);
        foodTextView.setText(foodStrings[i]);

        // for Show price
        TextView priceTextView = (TextView) objView1.findViewById(R.id.txtShowPrice);
        priceTextView.setText(priceString[i]);

        //  for  Icon

        ImageView iconImageView = (ImageView) objView1.findViewById(R.id.imvIcon);



        return objView1;
    }
}// Main
