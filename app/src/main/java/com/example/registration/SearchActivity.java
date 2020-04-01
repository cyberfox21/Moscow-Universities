package com.example.registration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.registration.DrivingActivity;
import com.example.registration.PanoramaActivity;
import com.example.registration.R;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraListener;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.CameraUpdateSource;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.VisibleRegionUtils;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.Response;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;
import com.yandex.mapkit.search.SearchOptions;
import com.yandex.mapkit.search.Session;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.runtime.network.NetworkError;
import com.yandex.runtime.network.RemoteError;



public class SearchActivity extends Activity implements Session.SearchListener, CameraListener {

    private final String MAPKIT_API_KEY = "43c9d950-1700-4d51-a9b1-817496ef789c";
    private EditText searchEdit;
    private MapView mapView;
    private SearchManager searchManager;
    private Session searchSession;
    private String title;
    Point resultLocation;
    private Double x, y;

    Context mContext;

    private void submitQuery(String query) {
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(mapView.getMap().getVisibleRegion()),
                new SearchOptions(),
                this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        SearchFactory.initialize(this);

        mContext = this;

        setContentView(R.layout.search);
        super.onCreate(savedInstanceState);

        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);

        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().addCameraListener(this);

        searchEdit = (EditText)findViewById(R.id.search_edit);

        Intent fromDescriptionActivity = getIntent();
        title = fromDescriptionActivity.getStringExtra("title");
        x = fromDescriptionActivity.getDoubleExtra("x", (float) 55.929729);
        y = fromDescriptionActivity.getDoubleExtra("y", (float) 37.520809);
        searchEdit.setText(title);

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    submitQuery(searchEdit.getText().toString());
                }

                return false;
            }
        });


        mapView.getMap().move(
                new CameraPosition(new Point(x, y), 14.0f, 0.0f, 0.0f));

        submitQuery(searchEdit.getText().toString());
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onSearchResponse(Response response) {
        MapObjectCollection mapObjects = mapView.getMap().getMapObjects();
        mapObjects.clear();

        for (GeoObjectCollection.Item searchResult : response.getCollection().getChildren()) {
            resultLocation = searchResult.getObj().getGeometry().get(0).getPoint();
            if (resultLocation != null) {
                mapObjects.addPlacemark(
                        resultLocation,
                        ImageProvider.fromResource(this, R.drawable.pointer));
            }
        }
    }

    @Override
    public void onSearchError(Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraPositionChanged(
            Map map,
            CameraPosition cameraPosition,
            CameraUpdateSource cameraUpdateSource,
            boolean finished) {
        if (finished) {
            submitQuery(searchEdit.getText().toString());
        }
    }

    public void openPanorama(View view) {
        Intent toPanoramaActivity = new Intent(getApplicationContext(), PanoramaActivity.class);
        toPanoramaActivity.putExtra("x", x);
        toPanoramaActivity.putExtra("y", y);
        startActivity(toPanoramaActivity);
    }

    public void buildRoute(View view) {
        Intent toDrivingActivity = new Intent(getApplicationContext(), DrivingActivity.class);
        toDrivingActivity.putExtra("x", x);
        toDrivingActivity.putExtra("y", y);
        toDrivingActivity.putExtra("resultX", resultLocation.getLatitude());
        toDrivingActivity.putExtra("resultY", resultLocation.getLongitude());
        startActivity(toDrivingActivity);
    }
}
