package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;

import java.util.List;

/**
 * Created by Faruk Karadeniz on 21.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class EventTask extends AsyncTask<String, Void, List<Event>> {
    private static final String TAG = EventTask.class.getSimpleName();
    private EventFilterActivity activity;
    private CollectionReference eventRef;
    private CollectionReference placeRef;

    public EventTask(Context context, CollectionReference eventRef, CollectionReference placeRef) {
        super();
        this.activity = ((EventFilterActivity) context);
        this.eventRef = eventRef;
        this.placeRef = placeRef;
    }

    @Override
    protected void onPreExecute() {
        //todo progressbar ile ya da yukardan cıkan notification ile indiriliyor tarzı bildirim
        super.onPreExecute();
    }

    @Override
    protected List<Event> doInBackground(String... strings) {
        for (String string : strings) {
            Log.i(TAG, "doInBackground: " + string);
        }
        String ilkTarih = strings[0];
        String ikinciTarih = strings[1];
        String sehir = strings[2];
        String category = strings[3];

        return null;
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        super.onPostExecute(events);
        //todo indirme tamamlandıktan sonra setresult yapıp diğer activity'e gönder
    }


}
