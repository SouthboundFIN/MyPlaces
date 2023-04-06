package fi.lab.fragmentexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class FragmentMaps extends Fragment {

    GoogleMap mMap;
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
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            //LatLng sydney = new LatLng(-34, 151);
            LatLng lahti = new LatLng(60.98267, 25.66151);
            Marker mr = googleMap.addMarker(new MarkerOptions().position(lahti).title("Lahti"));
            mr.setDraggable(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(lahti));
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
//          1)  anonymous instance who implements GoogleMap.OnMapClickListener
//            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(@NonNull LatLng latLng) {
//
//                }
//            });
//          2) lambda
            // to add a new marker..
            mMap.setOnMapClickListener(latLng -> {
                    Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title("M"+new Random().nextInt()));
                    m.setDraggable(true);
                });
            // to remove a marker...
            mMap.setOnMarkerClickListener(m -> {
                // optionally open a new activity containing marker info (whatever it is)
                // and question "ok to remove?" If ok, remove...
                m.remove();
                Toast.makeText(getContext(), "Marker: "+m.getTitle()+" "+m.getId()+" removed", Toast.LENGTH_SHORT).show();
                return true; // true: we handled the event, false: default actions still by parent
            });
            // if you set added markers draggable, you may implement this also...
            mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(@NonNull Marker marker) {

                }
                // ...at least this event when dragging is over (to update marker pos in db)
                @Override
                public void onMarkerDragEnd(@NonNull Marker marker) {
                    Toast.makeText(getContext(), "MarkerEnd: "+marker.getPosition(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDragStart(@NonNull Marker marker) {

                }
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
        searchView = getActivity().findViewById(R.id.searchLocation);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addressList;
                String location = searchView.getQuery().toString().trim();
                try {
                    // this is deprecated from API33.. (try-catch needed)
                    addressList = geocoder.getFromLocationName(location, 1);
                    // check if not valid user input
                    if(addressList != null && addressList.size() > 0){
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    }
                } catch(IOException e){

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}