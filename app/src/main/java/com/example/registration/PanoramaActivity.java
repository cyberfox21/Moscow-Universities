package com.example.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.places.PlacesFactory;
import com.yandex.mapkit.places.panorama.NotFoundError;
import com.yandex.mapkit.places.panorama.PanoramaService;
import com.yandex.mapkit.places.panorama.PanoramaView;
import com.yandex.runtime.Error;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;


public class PanoramaActivity extends Activity implements PanoramaService.SearchListener {

    private final String MAPKIT_API_KEY = "43c9d950-1700-4d51-a9b1-817496ef789c";
    private Point SEARCH_LOCATION;
    private PanoramaView panoramaView;
    private PanoramaService panoramaService;
    private PanoramaService.SearchSession searchSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent fromDescriptionActivity = getIntent();
        Double x, y;
        x = fromDescriptionActivity.getDoubleExtra("x", 0);
        y = fromDescriptionActivity.getDoubleExtra("y", 0);
        SEARCH_LOCATION = new Point(x, y);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        PlacesFactory.initialize(this);
        setContentView(R.layout.activity_panorama);
        super.onCreate(savedInstanceState);
        panoramaView = (PanoramaView)findViewById(R.id.panoview);

        panoramaService = PlacesFactory.getInstance().createPanoramaService();
        searchSession = panoramaService.findNearest(SEARCH_LOCATION, this);
    }

    @Override
    protected void onStop() {
        panoramaView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        panoramaView.onStart();
    }

    @Override
    public void onPanoramaSearchResult(String panoramaId) {
        panoramaView.getPlayer().openPanorama(panoramaId);
        panoramaView.getPlayer().enableMove();
        panoramaView.getPlayer().enableRotation();
        panoramaView.getPlayer().enableZoom();
        panoramaView.getPlayer().enableMarkers();
    }

    @Override
    public void onPanoramaSearchError(@NonNull Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof NotFoundError) {
            errorMessage = getString(R.string.not_found_error_message);
        } else if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

}
