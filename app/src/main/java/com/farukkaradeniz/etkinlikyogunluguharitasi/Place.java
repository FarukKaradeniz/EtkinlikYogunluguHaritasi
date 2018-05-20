package com.farukkaradeniz.etkinlikyogunluguharitasi;

import com.google.firebase.firestore.Exclude;

/**
 * Created by Faruk Karadeniz on 20.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class Place {
    private String name;
    private String city;
    private String streetAddress;
    private String address;
    private double lat = 200; //lat max/min +90 to -90
    private double lng = 200;//lng max/min +180 to -180
    private int capacity; //Mekanın kapasitesi
    private int averageGuestCount; //Mekandaki etkinliklerin ortalama katılımcı sayisi
    private int crowdGrade; //Mekanın ortalama yoğunluk derecesi Min:1, Max:5

    public Place() { /*Required no argument constructor for firestore*/ }

    public Place(String name, String city, String streetAddress, String address) {
        this.name = name;
        this.city = city;
        this.streetAddress = streetAddress;
        this.address = address;
    }

    /**
     * Konum bilgileri henuz olusturulmadiysa false degeri donecektir
     * Eger konum bilgileri set edildiyse true degeri donecektir
     */
    @Exclude
    public boolean isLocationExist() {
        return !(lat == 200 || lng == 200);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("{\n(name: %s)\n(lat: %s)\n" +
                        "(lng: %s)\n(address: %s)\n}",
                name, lat, lng, address);
    }

    public int getCrowdGrade() {
        return crowdGrade;
    }

    public void setCrowdGrade(int crowdGrade) {
        this.crowdGrade = crowdGrade;
    }

    public int getAverageGuestCount() {
        return averageGuestCount;
    }

    public void setAverageGuestCount(int averageGuestCount) {
        this.averageGuestCount = averageGuestCount;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}