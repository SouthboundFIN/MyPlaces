package fi.lab.fragmentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import java.time.LocalDate;
import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) fragments will be instantiated this way
        FragmentDate fragmentDate = FragmentDate.newInstance("Date","KekeBirth",
                LocalDate.of(1948,12,6));
        FragmentTime fragmentTime = FragmentTime.newInstance("Time","Keke", LocalTime.of(23,59));
            // this should be replaced by .newInstance()...
        FragmentMaps fragmentMaps = new FragmentMaps();
        // 2a) Let's initially replace a layout (for date & time) in parent activity
        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.flFragment,fragmentTime) // the replacement...
                .replace(R.id.flFragment,fragmentDate) // the replacement...
                // this will add to android device back stack but not good idea
                // because we are about to start the app...
                //.addToBackStack(null) // "global" unnamed backstack
                .commit(); // and finally commit is needed

        // 2b) Let's initially replace a layout (for google maps)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment2,fragmentMaps) // the replacement...
                // this will add to android device back stack but not good idea
                // because we are about to start the app...
                //.addToBackStack(null) // "global" unnamed backstack
                .commit(); // and finally commit is needed

        // 3) let's create onclick events for the buttons
        Button buttonShowDate = findViewById(R.id.buttonShowFragmentDate);
        Button buttonShowtime = findViewById(R.id.buttonShowFragmentTime);
        buttonShowDate.setOnClickListener(
                v -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment,fragmentDate)
                // when button clicked --> maybe make sense to add to backstack
                .addToBackStack(null) //
                .commit());
        buttonShowtime.setOnClickListener(
                v -> getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment,fragmentTime)
                        // when button clicked --> maybe make sense to add to backstack
                        .addToBackStack(null) //
                        .commit());
    }
}