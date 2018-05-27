package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.Exclude;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Faruk Karadeniz on 20.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class Event implements ClusterItem, Parcelable {
    private String name;
    private String date;
    private String link;
    @Exclude
    private Place place;
    private boolean isEventHappened = false; //True olduktan sonra numberOfParticipants 0'dan farklı olacak
    private String placeName;
    private Category category;
    private int numberOfParticipants = 0; // Etkinliğe katılım sayısı

    public Event() { /*Required no argument constructor for firestore*/ }

    public Event(String name, String date,
                 String link, Place place, Category category) {
        this.name = name;
        this.date = date;
        this.link = link;
        this.place = place;
        this.category = category;
        this.placeName = place.getName();
    }

    @Override
    public String toString() {
        return String.format("{\n(name: %s)\n(date: %s)\n" +
                        "(link: %s)\n(place: %s)\n(category: %s)\n}",
                name, date, link, place, category);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event) {
            Event other = (Event) obj;
            return this.name.equals(other.name) &&
                    this.link.equals(other.link) &&
                    this.date.equals(other.date);
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Exclude
    public Place getPlace() {
        return place;
    }

    @Exclude
    public void setPlace(Place place) {
        this.place = place;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public boolean isEventHappened() {
        return isEventHappened;
    }

    public void setEventHappened(boolean eventHappened) {
        isEventHappened = eventHappened;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(this.place.getLat(), this.place.getLng());
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    @Override
    public String getSnippet() {
        return this.date;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Exclude
    @Override
    public int describeContents() {
        return 0;
    }

    @Exclude
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.link);
        dest.writeParcelable(this.place, flags);
        dest.writeByte(this.isEventHappened ? (byte) 1 : (byte) 0);
        dest.writeString(this.placeName);
        dest.writeParcelable(this.category, flags);
        dest.writeInt(this.numberOfParticipants);
    }

    protected Event(Parcel in) {
        this.name = in.readString();
        this.date = in.readString();
        this.link = in.readString();
        this.place = in.readParcelable(Place.class.getClassLoader());
        this.isEventHappened = in.readByte() != 0;
        this.placeName = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.numberOfParticipants = in.readInt();
    }

    @Exclude
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
