package com.oasit.bomberos_seguridad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;



public class info_emergencia extends AppCompatActivity {
    RadioGroup grupo_radio;
    TextView mensaje1, conecto,mensaje2,id_diregps;
    EditText id_barrio, id_direccion, id_descripcion;

    private String tipo1;
    private String clase1;
    private String barrio1;
    private String direccion1;
    private String descripcion;
    private String sitio_lac;
    private String sitio_log;
    private String sitio_dir;
    private String gps_lac;
    private String gps_log;
    private String unidad;

    String grupo1[];
    private AsyncHttpClient cliente;
    Spinner spinner;
    TextView bot_solicitar,bto_no_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_emergencia);

        grupo_radio = findViewById(R.id.id_Grupo);

        mensaje1 = findViewById(R.id.id_gps);
        mensaje2 = findViewById(R.id.id_gps2);
        id_barrio = findViewById(R.id.id_barrio_e);
        id_direccion = findViewById(R.id.id_direccion_e);
        id_descripcion = findViewById(R.id.id_descripcion_e);
        id_diregps = findViewById(R.id.id_diregps);
        conecto = findViewById(R.id.conecto);
        bot_solicitar = findViewById(R.id.id_boton);
        bto_no_sitio = findViewById(R.id.id_no_sitio);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Tipos_incidentes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final String[] tipo = getResources().getStringArray(R.array.Tipos_incidentes);
        final String[] incendio =  getResources().getStringArray(R.array.INCENDIO);
        final String[] rescate  = getResources().getStringArray(R.array.RESCATE);
        final String[] accidente = getResources().getStringArray(R.array.ACCIDENTES);
        final String[] naturales = getResources().getStringArray(R.array.NATURALES);
        final String[] matpel = getResources().getStringArray(R.array.MATPEL);
        final String[] prevencion = getResources().getStringArray(R.array.PREVENCION);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        //fin localización
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                setTipo1(item);
                grupo_radio.removeAllViews();
                if (item.equals(tipo[0])) {
                    grupo1 = prevencion;
                    for (int i = 0; i < prevencion.length; i++ ) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(prevencion[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                } else if (item.equals(tipo[1])) {
                    grupo1 = incendio;
                    for (int i = 0; i < incendio.length; i++ ) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(incendio[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                } else if (item.equals(tipo[2])) {
                    grupo1 = rescate;
                    for (int i = 0; i < rescate.length; i++ ) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(rescate[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                } else if (item.equals(tipo[3])) {
                    grupo1 = matpel;
                    for (int i = 0; i <  matpel.length; i++) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(matpel[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                } else if (item.equals(tipo[4])) {
                    grupo1 = naturales;
                    for (int i = 0; i <  naturales.length; i++ ) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(naturales[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                } else if (item.equals(tipo[5])) {
                    grupo1 = accidente;
                    for (int i = 0; i <  accidente.length; i++  ) {
                        RadioButton rb = new RadioButton(info_emergencia.this);
                        rb.setText(accidente[i]);
                        rb.setId(i);
                        grupo_radio.addView(rb);
                    }
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                for (String item : prevencion) {
                    RadioButton rb = new RadioButton(info_emergencia.this);
                    rb.setText(item);
                    grupo_radio.addView(rb);

                }
            }
        });
        grupo_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getApplicationContext(), grupo1[checkedId], Toast.LENGTH_SHORT).show();
                setClase1(grupo1[checkedId]);
            }
        });
        bto_no_sitio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info_emergencia.this, mapa_selecion_sitio.class);
                startActivityForResult(intent, 2);
            }
        });

        bot_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBarrio1(id_barrio.getText().toString());
                setDireccion1(id_direccion.getText().toString());
                setDescripcion(id_descripcion.getText().toString());
                if(getSitio_lac() == null)
                {
                    setSitio_lac(getGps_lac());
                    setSitio_log(getGps_log());
                    setLocation(Double.parseDouble(getGps_lac()),Double.parseDouble(getGps_log()));
                }
                ejecutarServicio("https://bomberosmadridb31.com/sql/prueba.php");
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // Get String data from Intent
                double longitud = data.getDoubleExtra("longitud", 0.0);
                double lactitud = data.getDoubleExtra("lactitud", 0.0);
                setSitio_lac(String.valueOf(lactitud));
                setSitio_log(String.valueOf(longitud));
                setLocation(lactitud,longitud);
                bto_no_sitio.setText("CAMBIAR SITIO");
                mensaje1.setText("Lactitud: "+ lactitud + "\n" + "Longitud: " + longitud);
                Toast.makeText(getApplicationContext(), String.valueOf(longitud) + "," + String.valueOf(lactitud), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

        mensaje1.setText("LOCALIZANDO...");


    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                locationStart();
                return;
            }
        }
    }



    public class Localizacion implements LocationListener {
        info_emergencia mainActivity;

        public info_emergencia getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(info_emergencia mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            setGps_lac(String.valueOf(loc.getLatitude()));
            setGps_log(String.valueOf(loc.getLongitude()));
            String Text = "Lat = "
                    + loc.getLatitude() + "\n Long = " + loc.getLongitude();

            mensaje2.setText(Text);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            mensaje2.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            mensaje2.setText("GPS Activado");
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
    public void setLocation(double lactitud, double longitud) {
        if (lactitud != 0.0 && longitud != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        lactitud, longitud, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    setSitio_dir(DirCalle.getAddressLine(0));
                    id_diregps.setText("Dirección: " + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ejecutarServicio(String URL){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Tipo",getTipo1());
                parametros.put("Clase",getClase1());
                parametros.put("Barrio",getBarrio1());
                parametros.put("Direccion",getDireccion1());
                parametros.put("Descripcion",getDescripcion());
                parametros.put("Sitio_lac",getSitio_lac());
                parametros.put("Sitio_log",getSitio_log());
                parametros.put("Sitio_dir",getSitio_dir());
                parametros.put("Gps_lac",getGps_lac());
                parametros.put("Gps_log",getGps_log());
                parametros.put("Unidad","Prueba");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        finish();
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getClase1() {
        return clase1;
    }

    public void setClase1(String clase1) {
        this.clase1 = clase1;
    }

    public String getBarrio1() {
        return barrio1;
    }

    public void setBarrio1(String barrio1) {
        this.barrio1 = barrio1;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSitio_lac() {
        return sitio_lac;
    }

    public void setSitio_lac(String sitio_lac) {
        this.sitio_lac = sitio_lac;
    }

    public String getSitio_log() {
        return sitio_log;
    }

    public void setSitio_log(String sitio_log) {
        this.sitio_log = sitio_log;
    }

    public String getSitio_dir() {
        return sitio_dir;
    }

    public void setSitio_dir(String sitio_dir) {
        this.sitio_dir = sitio_dir;
    }

    public String getGps_lac() {
        return gps_lac;
    }

    public void setGps_lac(String gps_lac) {
        this.gps_lac = gps_lac;
    }

    public String getGps_log() {
        return gps_log;
    }

    public void setGps_log(String gps_log) {
        this.gps_log = gps_log;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}

