package com.oasit.bomberos_seguridad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;

public class info_emergencia extends AppCompatActivity {
    RadioGroup pruebas;
    TextView prueba, mensaje1, conecto;
    EditText id_barrio, id_direccion, id_descripcion;
    public String id_servicio;
    public String nombre_usuario;
    public String tipo_emergencia;
    private String tipo;
    public String barrio;
    public String direccion;
    public String descripcion;
    public String direccion_gps;
    public long numero_cel;
    double longitud, lactitud;
    private AsyncHttpClient cliente;
    private String codigo;
    Spinner spinner;
    TextView bot_solicitar,bto_no_sitio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_emergencia);

        pruebas = findViewById(R.id.id_Grupo);


        mensaje1 = findViewById(R.id.id_gps);
        id_barrio = findViewById(R.id.id_barrio_e);
        id_direccion = findViewById(R.id.id_direccion_e);
        id_descripcion = findViewById(R.id.id_descripcion_e);
        conecto = findViewById(R.id.conecto);
        bot_solicitar = findViewById(R.id.id_boton);
        bto_no_sitio = findViewById(R.id.id_no_sitio);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Tipos_incidentes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        String tipo[] = getResources().getStringArray(R.array.Tipos_incidentes);
        String incendio[] =  getResources().getStringArray(R.array.INCENDIO);
        String rescate [] = getResources().getStringArray(R.array.RESCATE);
        String accidente[]= getResources().getStringArray(R.array.ACCIDENTES);
        String naturales[]= getResources().getStringArray(R.array.NATURALES);
        String matpel[]= getResources().getStringArray(R.array.MATPEL);
        String[] prevencion = getResources().getStringArray(R.array.PREVENCION);

        for ( String this_currency_option: prevencion ) {
            RadioButton rb = new RadioButton(this);
            rb.setText(this_currency_option);
            pruebas.addView(rb);
        }

    }
}