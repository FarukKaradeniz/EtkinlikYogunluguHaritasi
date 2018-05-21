package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Faruk Karadeniz on 20.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class Utils {
    /**
     * Returns the distance between 2 locations in meters
     *
     * @param first  First location
     * @param second Second location
     * @return Distance between First and Second locations in meters
     */
    public static double getDistance(LatLng first, LatLng second) {
        Location firstLocation = new Location("First Location");
        firstLocation.setLatitude(first.latitude);
        firstLocation.setLongitude(first.longitude);
        Location secondLocation = new Location("Second Location");
        secondLocation.setLongitude(second.longitude);
        secondLocation.setLatitude(second.latitude);
        return firstLocation.distanceTo(secondLocation);
    }

    public static boolean isWithinXmeter(LatLng first, LatLng second, int x) {
        double distance = Utils.getDistance(first, second);
        return !(distance > x);
    }
}
