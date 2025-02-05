package com.administra.feriaCaballo.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.administra.feriaCaballo.Model.Menu;
import com.administra.feriaCaballo.R;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Menu> datos;


    public MenuAdapter(Context context, ArrayList datos) {
        super(context, R.layout.renglon_menu, datos);

        this.context = context;
        this.datos = datos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.renglon_menu, null);

        //Imagen
        ImageView imagen = (ImageView) item.findViewById(R.id.imagen_menu);
        switch (datos.get(position).id){
            case 1:
                imagen.setImageResource(R.drawable.profile_64);
                break;
            case 2:
                imagen.setImageResource(R.drawable.venue_64);
                break;
            case 3:
                imagen.setImageResource(R.drawable.contact_64);
                break;
            case 4:
                imagen.setImageResource(R.drawable.help_64);
                break;
            case 5:
                imagen.setImageResource(R.drawable.logout_24);
                break;
            case 6:
                imagen.setImageResource(R.drawable.venue_64);
                break;




        }
        //Nombre
        TextView texto = (TextView) item.findViewById(R.id.texto_menu);
        texto.setText(datos.get(position).texto);


        return item;

    }
}
