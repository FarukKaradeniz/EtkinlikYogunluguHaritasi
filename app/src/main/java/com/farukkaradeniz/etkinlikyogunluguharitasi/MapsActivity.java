package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private UiSettings uiSettings;
    private FloatingActionButton showEvent, makeRoute, clearMap;
    private FloatingActionsMenu floatMenu;
    private Polyline route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        init();
        listeners();

    }

    private void init() {
        showEvent = findViewById(R.id.action_event);
        makeRoute = findViewById(R.id.action_route);
        clearMap = findViewById(R.id.action_clear);
        floatMenu = findViewById(R.id.float_menu);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void listeners() {
        makeRoute.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, MakeRouteActivity.class), 11);
        });
        showEvent.setOnClickListener(view -> {
            Toast.makeText(this, "Show Event", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this, EventFilterActivity.class), 22);
        });
        clearMap.setOnClickListener(view -> {
            Toast.makeText(this, "Clear the Map", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(41.052, 29.001), 14f));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41, 29)).title("XXXXX"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.traffic) {
            Toast.makeText(this, "Traffic", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: ");
        if (requestCode == 11 && resultCode == RESULT_OK) {
            floatMenu.collapse();
            ArrayList<LatLng> points = data.getParcelableArrayListExtra("points");
            createRoute(points);
            Log.i(TAG, "onActivityResult: Route points size: " + points.size());
        } else if (requestCode == 22 && resultCode == RESULT_OK) {
            //XXXXXDASADASD
            Log.i(TAG, "onActivityResult: 22");
        }
    }

    private void createRoute(ArrayList<LatLng> points) {
        if (route != null && route.isVisible()) {
            route.remove();
        }
        PolylineOptions options = new PolylineOptions();
        options.addAll(points);
        options.width(10);
        options.color(Color.BLUE);
        route = mMap.addPolyline(options);
    }
}
