package baac.it.natoshi.baacrestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BAAC on 19/10/2558.
 */
public class FoodTABLE {
    //Explicit
    private MyOpenHelper obMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase;
    private static  final  String FOOD_TABLE ="FoodTABLE";
    private  static  final  String  COLUMN_ID_FOOD = "_id";
    private static final String COLUMN_FOOD = "Food";
    private static final String COLUMN_SOURCE = "Source";
    private static final String COLUMN_PRICE = "Price";


    public FoodTABLE(Context context) {
    obMyOpenHelper = new MyOpenHelper(context);
        writeSqLiteDatabase = obMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = obMyOpenHelper.getReadableDatabase();

    }
    public  long addNewFood(String strFood,String  strSource,String  strPrice) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_FOOD,strFood);
        objContentValues.put(COLUMN_SOURCE, strSource);
        objContentValues.put(COLUMN_PRICE, strPrice);
        return writeSqLiteDatabase.insert(FOOD_TABLE,null,objContentValues);

    }
}
