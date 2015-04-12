package proyecto.ludwingixcayau.modulo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by ludwing ixcayau on 11/04/2015.
 */
public class AdaptadorP extends ArrayAdapter<Producto> {

    private Producto[] datos;

    public AdaptadorP(Context context, Producto[] datos) {
        super(context, R.layout.productoitem, datos);
        this.datos = datos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.productoitem, null);

        TextView lblNombre = (TextView)item.findViewById(R.id.lblNombre);
        lblNombre.setText(datos[position].getNombre());
        TextView lblCantidad = (TextView)item.findViewById(R.id.lblCantidad);
        lblCantidad.setText(String.valueOf(datos[position].getCantidad()));

        return (item);
    }
}