package jaj.tct.com.onthebeach.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import jaj.tct.com.onthebeach.R;
import jaj.tct.com.onthebeach.models_geolocalizacao.Location;

public class MapAbrangente extends Fragment implements OnMapReadyCallback{

    private GoogleMap mGoogleMap;
    private MapView mapView;
    private Location location;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_abrangente, container, false);

        mapView = (MapView) v.findViewById(R.id.mapAbrangente);
        if(getArguments()!=null) {
            location = (Location) getArguments().getSerializable("map");
        }
        return  v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        LatLng home;
        if(location!=null){
            home = new LatLng(location.getLat(),  location.getLng());
        }else{
            home = new LatLng(13.678305, -89.276887);
        }
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mGoogleMap.addMarker(new MarkerOptions().position(home).title("Marker"));
        mGoogleMap.setMinZoomPreference(15);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
