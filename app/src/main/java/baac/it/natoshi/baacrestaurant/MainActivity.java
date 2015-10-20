package baac.it.natoshi.baacrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // Exp
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create & Conn  Database
        createAndConnected();

        //Tester Add new value
        // testerAdd();
        //  testerAddFood();
        //Delete All SQLite
        deleteAllSQLite();

        //synchronze JSON to SQLite

        synJsontoSQLite();
    } // main Methodgit

    private void synJsontoSQLite() {

        //0 Change Policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(myPolicy);
        int intTimes = 0;
        while (intTimes <= 1) {
            InputStream objInputStream = null;
            String strJSON = null;
            String strUSERURL = "http://swiftcodingthai.com/baac/php_get_data_master.php";
            String strFOODURL = "http://swiftcodingthai.com/baac/php_get_food.php";
            HttpPost objHttpPost;

            //objInputStream.
            //1 Create InputStream
            try {

                HttpClient objHttpClient = new DefaultHttpClient();
                switch (intTimes) {
                    case 0:
                        objHttpPost = new HttpPost(strUSERURL);
                        break;
                    case 1:
                        objHttpPost = new HttpPost(strFOODURL);
                        break;
                    default:
                        objHttpPost = new HttpPost(strUSERURL);
                        break;
                }//switch

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent();
            } catch (Exception e) {
                Log.d("baac", "InputStream ===>" + e.toString());
            }


            //2 Create JSON String

            try {
                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;
                while ((strLine = objBufferedReader.readLine()) != null) {

                    objStringBuilder.append(strLine);


                }//while
                objInputStream.close();
                strJSON = objStringBuilder.toString();


            } catch (Exception e) {
                Log.d("baac", "strJSON ==>" + e.toString());
            }

            //3 Update SQLite
            try {

                JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject object = objJsonArray.getJSONObject(i);

                    switch (intTimes) {
                        case 0:

                            //for UserTABLE
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            objUserTABLE.addNewUser(strUser, strPassword, strName);


                            break;
                        case 1:

                            //For foodTABLA
                            String strFood = object.getString("Food");
                            String strSource = object.getString("Source");
                            String strPrice = object.getString("Price");
                            objFoodTABLE.addNewFood(strFood, strSource, strPrice);


                            break;

                    }


                }//for

            } catch (Exception e) {
                Log.d("baac", "Update ==>" + e.toString());
            }


            intTimes += 1;
        }
    }

    private void deleteAllSQLite() {
        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("BAAC.db", MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE", null, null);
        objSqLiteDatabase.delete("foodTABLE", null, null);
    }


    private void testerAddFood() {
        objFoodTABLE.addNewFood("อาหาร ", "testSource", "1000");
    }

    private void testerAdd() {

        objUserTABLE.addNewUser("testUSer", "testPassword", "พัฒนเกียรติ");
    }

    private void createAndConnected() {
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);
    }


}//  main