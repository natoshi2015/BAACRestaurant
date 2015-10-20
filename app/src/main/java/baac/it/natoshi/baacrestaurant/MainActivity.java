package baac.it.natoshi.baacrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    } // main Methodgit

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