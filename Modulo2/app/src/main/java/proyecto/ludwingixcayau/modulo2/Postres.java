package proyecto.ludwingixcayau.modulo2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Postres extends Fragment {


    public Postres() {
        // Required empty public constructor
    }

    private ListView listaPostres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_postres,container,false);
        listaPostres = (ListView) rootView.findViewById(R.id.listaPostres);
        String[] datos = new String[]{
                "Flan", "Helado Grande", "Pie Queso", "McCono", "McFlury"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);

        listaPostres.setAdapter(adapter);

        registerForContextMenu(listaPostres);
        return rootView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();

        if(v.getId() == R.id.listaPostres){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(
                    listaPostres.getAdapter().getItem(info.position).toString()
            );
            inflater.inflate(R.menu.menu_agregar, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.Agregar:
                Toast.makeText(getActivity(), listaPostres.getAdapter().getItem(info.position).toString() + " agregada exitosamente", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Cancelar:
                Toast.makeText(getActivity(),"Transaccion Cancelada",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
