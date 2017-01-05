package nsru.demsomboon.ratchasak.phijitcoffee;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latShopDouble=16.027050;
    private double lngShopDouble= 100.630135;//location of shop


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }//main method



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       //Set Map
        LatLng latLng=new LatLng(latShopDouble,lngShopDouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));


        //create marker

        mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
        .title(getResources().getString(R.string.app_name))
        .snippet(getResources().getString(R.string.address_shop)));

    }//on map ready
    public void clickBackMap(View view){
        finish();
    }
}//mainclass
