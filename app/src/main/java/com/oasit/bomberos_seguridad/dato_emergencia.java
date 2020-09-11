package com.oasit.bomberos_seguridad;

import android.content.res.Resources;
import android.widget.RadioGroup;

public class dato_emergencia {
    String Barrio;
    String dirección;
    String descripcion;
    String unidad;
    double lac;
    double log;
    double lac_no;
    double log_no;
    long consecutivo;
    Resources res = Resources.getSystem();
    String tipo[] = res.getStringArray(R.array.Tipos_incidentes);
    String incendio[] =  res.getStringArray(R.array.INCENDIO);
    String rescate [] = res.getStringArray(R.array.RESCATE);
    String accidente[]= res.getStringArray(R.array.ACCIDENTES);
    String naturales[]= res.getStringArray(R.array.NATURALES);
    String matpel[]= res.getStringArray(R.array.MATPEL);
    String prevencion []= res.getStringArray(R.array.PREVENCION);

}
