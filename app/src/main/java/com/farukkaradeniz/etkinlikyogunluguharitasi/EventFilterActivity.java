package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventFilterActivity extends AppCompatActivity {

    private static final String TAG = EventFilterActivity.class.getSimpleName();
    private Spinner dates, cities, categories;
    private Button tamam, tarihSec;
    private TextView secilen;
    private DatePickerDialog dialog;
    private Calendar cal;
    private SimpleDateFormat sdf;
    private String formatDate, format2Date;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_filter);
        init();
        listeners();

    }

    private void listeners() {
        dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String date = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "onItemSelected: " + date);
                tarihSec.setVisibility(date.equals("Başka bir tarih") ? View.VISIBLE : View.GONE);
                if (date.equals("Yarın")) {
                    cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);

                    formatDate = sdf.format(cal.getTime());
                    format2Date = "";
                    secilen.setText("Secilen Tarih: " + formatDate);
                } else if (date.equals("Bu Haftasonu")) {
                    cal = Calendar.getInstance();
                    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        haftasonuHesapla();
                    } else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        Date pazar = cal.getTime();
                        formatDate = sdf.format(pazar);
                        format2Date = "";
                        secilen.setText("Secilen Tarih: " + formatDate);
                    } else {
                        while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                            cal.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        haftasonuHesapla();
                    }
                } else if (date.equals("Bugün")) {
                    formatDate = sdf.format(new Date());
                    format2Date = "";
                    secilen.setText("Secilen Tarih: " + formatDate);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tarihSec.setOnClickListener(view -> {
            dialog.show();
        });
        tamam.setOnClickListener(view -> {
            //todo ASYNCTASK başlatılacak bitince geri gidilecek eventlerle beraber
            new EventTask(this, firestore.collection("events"), firestore.collection("places"))
                    .execute(formatDate, format2Date,
                            (String) cities.getSelectedItem(),
                            (String) categories.getSelectedItem());
        });
    }

    private void haftasonuHesapla() {
        Date ctesi = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date pazar = cal.getTime();
        formatDate = sdf.format(ctesi);
        format2Date = sdf.format(pazar);
        secilen.setText("Secilen Tarihler: \n" + formatDate + "\n" + format2Date);
    }

    @SuppressLint("SimpleDateFormat")
    private void init() {
        firestore = FirebaseFirestore.getInstance();
        secilen = findViewById(R.id.secilen_tarih);
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        secilen.setText("Secilen Tarih: " + formatDate);
        formatDate = sdf.format(cal.getTime());
        format2Date = "";
        dates = findViewById(R.id.spinner_date);
        dates.setPrompt("Lütfen Tarih Seçiniz");
        cities = findViewById(R.id.spinner_cities);
        cities.setPrompt("Lütfen Şehir Seçiniz");
        categories = findViewById(R.id.spinner_categories);
        categories.setPrompt("Lütfen Kategori Seçiniz");
        tamam = findViewById(R.id.tamam_button);
        tarihSec = findViewById(R.id.pick_date);
        dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            Log.i(TAG, "init: " + i + "-" + i1 + "-" + i2);
            cal.set(dialog.getDatePicker().getYear(),
                    dialog.getDatePicker().getMonth(),
                    dialog.getDatePicker().getDayOfMonth(),
                    0, 0, 0);
            Date date = cal.getTime();
            formatDate = sdf.format(date);
            format2Date = "";
            secilen.setText("Secilen Tarih: " + formatDate);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }
}