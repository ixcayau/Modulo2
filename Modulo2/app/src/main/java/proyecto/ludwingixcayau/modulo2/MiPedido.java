package proyecto.ludwingixcayau.modulo2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiPedido extends Fragment {

    private ListView listaOpciones;
    private TextView lblOpSeleccionada;
    private Producto[] datos = new Producto[]{
            new Producto("Quesoburguesa", "Comida",0,1),
            new Producto("Coca Cola", "Bebida",0,1)
    };
    public MiPedido() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mi_pedido,container,false);

        listaOpciones = (ListView) rootView.findViewById(R.id.listaMisProductos);
        lblOpSeleccionada = (TextView) rootView.findViewById(R.id.lblSeleccion);

        AdaptadorP adaptador = new AdaptadorP(getActivity(), datos);
        listaOpciones.setAdapter(adaptador);

        listaOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Producto item = (Producto)parent.getItemAtPosition(position);

                lblOpSeleccionada.setText(item.getTipo()+": " + item.getNombre()+" Q"+(item.getPrecio()*item.getCantidad())+" (Q"+item.getPrecio()+")");

            }
        });
        return rootView;
    }


}
