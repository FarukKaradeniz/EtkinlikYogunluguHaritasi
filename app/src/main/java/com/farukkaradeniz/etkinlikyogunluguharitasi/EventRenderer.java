package com.farukkaradeniz.etkinlikyogunluguharitasi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by Faruk Karadeniz on 20.05.2018.
 * Twitter: twitter.com/Omeerfk
 * Github: github.com/FarukKaradeniz
 * LinkedIn: linkedin.com/in/FarukKaradeniz
 * Website: farukkaradeniz.com
 */
public class EventRenderer extends DefaultClusterRenderer<Event> {
    private static final String TAG = EventRenderer.class.getSimpleName();
    private Context context;
    private IconGenerator ic;

    public EventRenderer(Context context, GoogleMap map, ClusterManager<Event> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        ic = new IconGenerator(context);
    }

    @Override
    protected void onBeforeClusterItemRendered(Event item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(getMarkerColor(item.getPlace().getCrowdGrade()));
        markerOptions.icon(markerDescriptor);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Event> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
        //TODO Daha iyi bir algoritma ile cluster'ların renkleri güncellenecek
        int clusterSize = cluster.getSize();
        Drawable clusterIcon = context.getDrawable(R.drawable.ic_circle_48dp);
        clusterIcon.setColorFilter(returnClusterColor(cluster), PorterDuff.Mode.SRC_ATOP);
        ic.setBackground(clusterIcon);
        if (clusterSize < 10) {
            ic.setContentPadding(40, 28, 0, 0);
        } else {
            ic.setContentPadding(32, 28, 0, 0);
        }

        /*TODO
         * TODO eğer aynı mekanda cluster oluşturacak kadar fazla etkinlik varsa orada marker değil cluster olarak görünüyor
         * TODO ama aynı gün içerisinde orada olan etkinlik sayısı çok fazla olmayabilir, cluster için gereken sayıyı artırılmalı
         * TODO şimdilik bu sayı 6'dan büyükse olarak ayarlanacak. aynı yerde aynı gün 6 etkinlik olmaz
         */
        Bitmap icon = ic.makeIcon(String.valueOf(clusterSize));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<Event> cluster) {
        return cluster.getSize() > 6;
    }

    private int returnClusterColor(Cluster<Event> cluster) {
        int avg = 0;
        for (Event event : cluster.getItems()) {
            avg += event.getPlace().getCrowdGrade();
        }
        avg /= cluster.getSize();
        switch (avg) {
            case 1:
                return context.getResources().getColor(R.color.grade_level_1);
            case 2:
                return context.getResources().getColor(R.color.grade_level_2);
            case 3:
                return context.getResources().getColor(R.color.grade_level_3);
            case 4:
                return context.getResources().getColor(R.color.grade_level_4);
            default:
                return context.getResources().getColor(R.color.grade_level_5);
        }
    }

    private float getMarkerColor(int grade) {
        switch (grade) {
            case 1:
                return 120; // grade_level_1
            case 2:
                return 62; // grade_level_2
            case 3:
                return 36; // grade_level_3
            case 4:
                return 0; // grade_level_4
            case 5:
                return 356; // grade_level_5 ama 4'e çok yakın bir renk
        }
        return 0f;
    }

}
