package fi.lab.myplaces;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class FragmentMaps extends Fragment {


    GoogleMap mapObj;
    SearchView searchView;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap mapObj) {
            // Array list to store the marker data

            LatLng tre = new LatLng(61.4979662395883, 23.76361569400841);
            Marker m0 = mapObj.addMarker(new MarkerOptions()
                    .position(tre)
                    .title("Tampere")
                    .snippet("lisätieto")
                    .draggable(true));
//          m0.setDraggable(true);
            mapObj.moveCamera(CameraUpdateFactory.newLatLng(tre));
            // Google Maps UI elements
            mapObj.getUiSettings().setAllGesturesEnabled(true);
//            mapObj.getUiSettings().setZoomControlsEnabled(true);
            mapObj.getUiSettings().setMapToolbarEnabled(false);



            mapObj.setOnMapClickListener(latLng -> {
                // TODO: Open new activity here with prompt to add new marker on clicked coordinates
                // User input (marker description) given in FragmentBottom EditText fields
                Marker m = mapObj.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("OTSIKKO")
                        .snippet("Lisätieto"));
//              Marker m = mapObj.addMarker(new MarkerOptions().position(latLng).title("M"+new Random().nextInt()));

            });
            // to remove a marker...
            mapObj.setOnMarkerClickListener(m -> {
                // TODO: optionally open a new activity containing marker info (whatever it is)
                // and question "ok to remove?" If ok, remove...
                m.remove();
                Toast.makeText(getContext(), "Marker: "+m.getTitle()+" removed", Toast.LENGTH_SHORT).show();
                return true; // true: we handled the event, false: default actions still by parent
            });



        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}