package baac.it.natoshi.baacrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind Widget
        bindWidget();

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

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);

    }

    public void clickLogin(View view) {
        userString = userEditText.getText().toString().trim();

        passwordString = passwordEditText.getText().toString().trim();
     //   errorDialog("Have Space","Please Fill All "+userString+" pass "+passwordString );
        if (userString.equals("") || passwordString.equals("")) {
            //Have space
             errorDialog("Have Space","Please Fill All ");
        } else {
            // No space
            checkUser();
        }


    }

    private void checkUser() {
// test
        try {
            String[] strMyResult = objUserTABLE.searchUser(userString);

            if (passwordString.equals(strMyResult[2])) {
                Toast.makeText(MainActivity.this,"Welcome"+strMyResult[3],Toast.LENGTH_LONG).show();

                //Intent to Order
                Intent objIntent = new Intent(MainActivity.this,OrderActivity.class);
                objIntent.putExtra("Name", strMyResult[3]);
                startActivity(objIntent);
                finish();

 //

            } else {
                errorDialog("password false","Please Try again Password ");
            }


        }catch (Exception e){
              errorDialog("No this user","No "+userString+" on my DB");
        }

    }

    private void errorDialog(String strTitle, String strMessage) {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
        //return;
    }

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