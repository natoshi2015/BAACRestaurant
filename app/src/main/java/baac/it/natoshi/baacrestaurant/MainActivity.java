package baac.it.natoshi.baacrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Exp
    private UserTABLE objUserTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create & Conn  Database
        createAndConnected();


    } // main Method

    private void createAndConnected() {

        objUserTABLE = new UserTABLE(this);
    }


}//  main