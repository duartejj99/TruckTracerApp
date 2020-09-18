package com.example.ultimategps;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {

    private Object LocationManager;
    private TextView tvTim, tvLong, tvLat, etIP, etIP2, etIP3, etIP4, etPort;
    String ipAddress1, ipAddress2, ipAddress3, ipAddress4, portServer, paquete;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTim = (TextView) findViewById(R.id.tvTime);
        tvLong = findViewById(R.id.tvLong);
        tvLat = findViewById(R.id.tvLat);
        etIP = findViewById(R.id.etIP);
        etIP2 = findViewById(R.id.etIP2);
        etIP3 = findViewById(R.id.etIP3);
        etIP4 = findViewById(R.id.etIP4);
        etPort = findViewById(R.id.etPort);

        //Check permission
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Cuando el permiso este concedido habilita metodo getlocalizacion
            configGPS();
        } else {
            //cuando  es denegado
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            //Hace un pedido nuevamente del permiso
        }
    }




   // Automático
    private void Enviar(){

        MessageSender messageSender = new MessageSender();
        messageSender.execute(paquete, ipAddress1, portServer);
       MessageSender messageSender2 = new MessageSender();
        messageSender2.execute(paquete, ipAddress2,  portServer);
       /* MessageSender messageSender3 = new MessageSender();
        messageSender3.execute(paquete, ipAddress3,  portServer);*/

    }

    public void Destino(View v){

        ipAddress1=etIP.getText().toString();
        ipAddress2=etIP2.getText().toString();
        ipAddress3=etIP3.getText().toString();
        ipAddress4=etIP4.getText().toString();
        portServer = etPort.getText().toString();


    }

    private void actualizaPantalla(Location location){

        Date date = new Date(location.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String formattedDate = sdf.format(date);

        tvTim.setText(String.valueOf(formattedDate));
        tvLat.setText( String.valueOf(location.getLatitude()));
        tvLong.setText(String.valueOf(location.getLongitude()));

        paquete= String.valueOf(location.getLatitude())+ "/"
                 +String.valueOf(location.getLongitude())+"/"
                +String.valueOf(formattedDate);


    }
    private void configGPS() {

        android.location.LocationManager mLocationManager = null;
        LocationManager = mLocationManager;
        MyLocationListener mLocationListener;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        mLocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new MyLocationListener();

        //llama al locationlistener cuando se cumplen las condiciones y usara el GPS_PROVIDER


        mLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,
                750, 0, mLocationListener);

    }
    private class MyLocationListener implements LocationListener {



        @Override
        //location contiene la info de altitud, longitud, latitud, orientacion, velocidad
        public void onLocationChanged(@NonNull Location location) {

            //Log.w(); //mensajes de warnings
            //Log.e();//msj de error
            Log.d("GPS_Update movement", "Latitude=" + String.valueOf(location.getLatitude()));//de depuracion, solo aparecen cuando el proyecto se firma con una clave
            Log.d("GPS_Update movement", "Longitud=" + String.valueOf(location.getLongitude()));
            Log.d("GPS_Update movement", "Stamptime=" + String.valueOf(location.getTime()));
            /*ipAddress1= "34.238.175.220";
            ipAddress2= "190.165.37.120";
            ipAddress3= "3.215.168.38";
            ipAddress4= "54.144.118.222";
            portServer="5000";*/
            actualizaPantalla(location);
            Enviar();
            
        }



       /* @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }*/
    }





}