package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Faruk Karadeniz on 11.06.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class ClusterListViewAdapter extends ArrayAdapter<Event> {
    private SimpleDateFormat sdf, tmp;

    public ClusterListViewAdapter(@NonNull Context context, @NonNull List<Event> objects) {
        super(context, 0, objects);
        sdf = new SimpleDateFormat("dd MMMM yyyy EEEE, HH:mm", new Locale("tr", "TR"));
        tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("tr", "TR"));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cluster_list_item, parent, false);
        }
        TextView name = convertView.findViewById(R.id.ec_name);
        TextView adres = convertView.findViewById(R.id.ec_adres);
        TextView grade = convertView.findViewById(R.id.ec_grade);
        TextView tarih = convertView.findViewById(R.id.ec_tarih);
        name.setText(event.getName());
        adres.setText(event.getPlace().getAddress());
        grade.setText("Yoğunluk: " + event.getPlace().getCrowdGrade());
        try {
            tarih.setText(sdf.format(tmp.parse(event.getDate())));
        } catch (ParseException e) {
            tarih.setText("Hata oluştu");
        }


        return convertView;
    }

}
