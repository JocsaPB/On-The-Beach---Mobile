package jaj.tct.com.onthebeach.Auxiliares;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jaj.tct.com.onthebeach.fragments.MapAbrangente;
import jaj.tct.com.onthebeach.models.Endereco;
import jaj.tct.com.onthebeach.models.Loja;
import jaj.tct.com.onthebeach.models_geolocalizacao.Location;
import jaj.tct.com.onthebeach.models_geolocalizacao.ResultadoGeolocalizacao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jocsa on 27/11/2016.
 */

public class ObterLatLng <Contexto extends AppCompatActivity>{

    //valores para completar url -> 1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=YOUR_API_KEY
    //private String URL_BUSCA = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String KEY_GEO = "AIzaSyB6sILct0A5rNFeOlA9OYBgVw4hKij3Zl0";

    private Endereco endereco;
    private Contexto ctx;
    private Location location;

    public ObterLatLng(){}

    public ObterLatLng(Endereco endereco, Contexto ctx){
        this.endereco = endereco;
        this.ctx = ctx;
        location = new Location();
        BuscarLatLng buscarLatLng = new BuscarLatLng();
        buscarLatLng.execute();
    }

    public class BuscarLatLng extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Geocoder geocoder = new Geocoder(ctx);
            List<Address> enderecos = new ArrayList<>();
            try {
                enderecos = geocoder.getFromLocationName(endereco.getCep().replace("-","")+endereco.getBairro()+endereco.getEstado().trim(),1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                Log.i("logGecoder","parametros ilegais");
            }
            if(enderecos.size()>0){
                Log.i("logGecoder","1 - Lat, Lng: "+enderecos.get(0).getLatitude()+", "+enderecos.get(0).getLongitude());
                location.setLat(enderecos.get(0).getLatitude());
                location.setLng(enderecos.get(0).getLongitude());
            }else {
                Log.i("logGecoder","Não houve nenhum resultado");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MapAbrangente mapAbrangente = new MapAbrangente();
            Bundle bundle = new Bundle();
            bundle.putSerializable("map",location);
            mapAbrangente.setArguments(bundle);
            ReplaceFragment replaceFragment = new ReplaceFragment(ctx);
            replaceFragment.replaceFragment(mapAbrangente, "LocationOnMapLoja");

        }
    }

}
