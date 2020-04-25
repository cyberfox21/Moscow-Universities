package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yandex.mapkit.GeoObjectCollection;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
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

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements Session.SearchListener, CameraListener, DrivingSession.DrivingRouteListener {

    private final String MAPKIT_API_KEY = "43c9d950-1700-4d51-a9b1-817496ef789c";
    private EditText searchEdit;
    private LinearLayout searchLayout;
    private MapView mapView;
    private SearchManager searchManager;
    private Session searchSession;
    private String title;
    private Point resultLocation;

    private Double end_x, end_y;
    private Double start_x, start_y;

    private Point ROUTE_START_LOCATION = new Point(59.959194, 30.407094);
    private Point ROUTE_END_LOCATION = new Point(55.733330, 37.587649);

    //private MapObjectCollection mapObjects;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;

    private Context mContext;

    private BottomNavigationView toolBar;

    private String KEY = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(MapActivity.this);
        SearchFactory.initialize(MapActivity.this);
        DirectionsFactory.initialize( MapActivity.this);

        Intent fromDescriptionActivity = getIntent();
        title = fromDescriptionActivity.getStringExtra("title");
        end_x = fromDescriptionActivity.getDoubleExtra("x", (Double) 55.733330);
        end_y = fromDescriptionActivity.getDoubleExtra("y", (Double) 55.733330);
        start_x = 55.751853;
        start_y = 37.679608;

        ROUTE_START_LOCATION = new Point(start_x, start_y);
        ROUTE_END_LOCATION = new Point(end_x, end_y);

        setContentView(R.layout.activity_map);

        hideSystemUI();

        mContext = MapActivity.this;
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().addCameraListener(MapActivity.this);
        searchLayout = findViewById(R.id.search_layout);
        searchEdit = (EditText)findViewById(R.id.search_edit);
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
                new CameraPosition(new Point(end_x, end_y), 14.0f, 0.0f, 0.0f));
        submitQuery(searchEdit.getText().toString());


        toolBar = findViewById(R.id.toolBar);
        toolBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_location:
                        if(!KEY.equals("location")) {
                            searchLayout.setVisibility(View.VISIBLE);
                            mContext = MapActivity.this;
                            mapView.getMap().addCameraListener(MapActivity.this);
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
                                    new CameraPosition(new Point(end_x, end_y), 14.0f, 0.0f, 0.0f));
                            submitQuery(searchEdit.getText().toString());
                            KEY = "location";
                        }else {
                            Toast.makeText(MapActivity.this, "Location уже загружен", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.action_route:
                        if(!KEY.equals("route")) {
                            searchLayout.setVisibility(View.INVISIBLE);
                            mapView = findViewById(R.id.mapview);
                            MapObjectCollection mapObjects = mapView.getMap().getMapObjects();
                            mapObjects.clear();
                            //mapObjects = mapView.getMap().getMapObjects().addCollection();
                            final Point SCREEN_CENTER = new Point(
                                    (ROUTE_START_LOCATION.getLatitude() + ROUTE_END_LOCATION.getLatitude()) / 2,
                                    (ROUTE_START_LOCATION.getLongitude() + ROUTE_END_LOCATION.getLongitude()) / 2);
                            mapView.getMap().move(new CameraPosition(
                                SCREEN_CENTER, 12, 0, 0));
                            drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
                            submitRequest();
                            KEY = "route";
                        }else {
                            Toast.makeText(MapActivity.this, "Route уже загружен", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void submitQuery(String query) {
        searchSession = searchManager.submit(
                query,
                VisibleRegionUtils.toPolygon(mapView.getMap().getVisibleRegion()),
                new SearchOptions(),
                (Session.SearchListener) this);
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

        //Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onCameraPositionChanged(
            Map map,
            CameraPosition cameraPosition,
            CameraUpdateSource cameraUpdateSource,
            boolean finished) {
        if (finished & KEY == "location") {
            submitQuery(searchEdit.getText().toString());
        }
    }
    @Override
    public void onDrivingRoutes(List<DrivingRoute> routes) {
        for (DrivingRoute route : routes) {
            MapObjectCollection mapObjects = mapView.getMap().getMapObjects();
            mapObjects.addPolyline(route.getGeometry());
        }
    }
    @Override
    public void onDrivingRoutesError(Error error) {
        String errorMessage = getString(R.string.unknown_error_message);
        if (error instanceof RemoteError) {
            errorMessage = getString(R.string.remote_error_message);
        } else if (error instanceof NetworkError) {
            errorMessage = getString(R.string.network_error_message);
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
    private void submitRequest() {
        DrivingOptions options = new DrivingOptions();
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(
                ROUTE_START_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        requestPoints.add(new RequestPoint(
                ROUTE_END_LOCATION,
                RequestPointType.WAYPOINT,
                null));
        drivingSession = drivingRouter.requestRoutes(requestPoints, options, this);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
