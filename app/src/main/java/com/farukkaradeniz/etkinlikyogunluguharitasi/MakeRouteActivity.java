package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MakeRouteActivity extends AppCompatActivity implements RoutingListener {
    private static final String TAG = MakeRouteActivity.class.getSimpleName();
    private Place source, destination;
    private PlaceAutocompleteFragment sourcePlaceFragment, destinationPlaceFragment;
    private Button makeRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_route);
        init();
        listeners();
    }

    private void init() {
        makeRoute = findViewById(R.id.btn_make_route);
        sourcePlaceFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.start_destination);

        destinationPlaceFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.stop_destination);

        sourcePlaceFragment.setHint("Başlangıç konumu giriniz");
        destinationPlaceFragment.setHint("Bitiş konumunu giriniz");
    }


    private void listeners() {
        sourcePlaceFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.toString());
                source = place;
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(MakeRouteActivity.this, "Error_S: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        destinationPlaceFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place2: " + place.toString());
                destination = place;
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred2: " + status);
                Toast.makeText(MakeRouteActivity.this, "Error_D: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        makeRoute.setOnClickListener(view -> createRoute());
    }

    private void createRoute() {
        if (source == null || destination == null) {
            Toast.makeText(this, "Unvalid locations", Toast.LENGTH_SHORT).show();
            return;
        }

        Routing routing = new Routing.Builder()
                .alternativeRoutes(false)
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .waypoints(source.getLatLng(), destination.getLatLng())
                .withListener(this)
                .build();
        routing.execute();

    }

    @Override
    public void onRoutingFailure(RouteException e) {

    }

    @Override
    public void onRoutingStart() {
        //TODO Progress bar göster. Success ve Failure'da bitir
        // Ya da şu : https://github.com/Tapadoo/Alerter kütüphane hoş duruyor onu da yapabilirsin
    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> arrayList, int i) {
        Route route = arrayList.get(0);
        Intent intent = getIntent();
        ArrayList<LatLng> points = ((ArrayList<LatLng>) route.getPoints());
        intent.putParcelableArrayListExtra("points", points);
        intent.putExtra("duration", route.getDurationText());
        intent.putExtra("distance", route.getDistanceText());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRoutingCancelled() {

    }
}
