package fi.lab.myplaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.LocalTime;


public class MainActivity extends AppCompatActivity {
    //ListView listViewMarkers; // TODO: ListView for saved markers



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize fragment
        Fragment mapFragment=new FragmentMaps();

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.FragmentTop,mapFragment)
                .commit();

    }
}