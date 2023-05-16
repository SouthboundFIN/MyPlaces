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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class FragmentMaps extends Fragment {

    private View rootView;
    private EditText editTextTitle;
    private EditText editTextSnippet;

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
            LatLng tre = new LatLng(61.4979662395883, 23.76361569400841);
            Marker m0 = mapObj.addMarker(new MarkerOptions()
                    .position(tre)
                    .title("Tampere")
                    .snippet("lisätieto"));
            m0.setTag(m0.getId());
            mapObj.moveCamera(CameraUpdateFactory.newLatLng(tre));
            // Google Maps UI elements
            mapObj.getUiSettings().setAllGesturesEnabled(true);
//          mapObj.getUiSettings().setZoomControlsEnabled(true);
            mapObj.getUiSettings().setMapToolbarEnabled(false);


            mapObj.setOnMapClickListener(latLng -> {
                if (!editTextTitle.getText().toString().equals("") ) {
                    addMarker(mapObj, latLng, editTextTitle.getText().toString().trim(), editTextSnippet.getText().toString().trim());
                    editTextTitle.setText("");
                    editTextSnippet.setText("");
                } else {
                    Toast.makeText(getContext(), "Please give at least a title for the new marker.", Toast.LENGTH_LONG).show();
                }
            });
            // to remove a marker...
            mapObj.setOnMarkerClickListener(m -> {

                // TODO: optionally open a new activity containing marker info (whatever it is)
                // and question "ok to remove?" If ok, remove...
                m.showInfoWindow();
                return true; // true: we handled the event, false: default actions still by parent
            });

            // TODO: Edit marker title and snippet texts marker info window is normal clicked
            mapObj.setOnInfoWindowClickListener(m -> {
            });

            // Delete marker when marker info window is long clicked
            mapObj.setOnInfoWindowLongClickListener(m -> {
                Toast.makeText(getContext(), "Marker: "+m.getTag()+" | "+m.getTitle()+" removed", Toast.LENGTH_LONG).show();
                m.setTag(null);
                m.remove();
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextSnippet = rootView.findViewById(R.id.editTextSnippet);
        return rootView;
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

    public void addMarker(GoogleMap mapObj, LatLng latLng, String title, String snippet) {
        Marker m = mapObj.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet(snippet));

                m.setTag(m.getId());
                m.showInfoWindow();
                System.out.println("The tag of the new marker is: "+m.getTag());
                System.out.println(m.getId()+" "+m.getPosition()+" "+m.getTitle()+" "+m.getSnippet());
//              mapObj.moveCamera(CameraUpdateFactory.newLatLng(m.getPosition())); // Move camera to marker

    }

}