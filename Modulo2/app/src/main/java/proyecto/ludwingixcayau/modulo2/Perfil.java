package proyecto.ludwingixcayau.modulo2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Perfil extends Fragment {


    public Perfil() {
        // Required empty public constructor
    }
    private TextView nombrePerfil;
    private TextView usuarioPerfil;
    private Button cerrarSesion;
    private TextView contraseñaPerfil;
    private SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        TextView nombrePerfil = (TextView)viewRoot.findViewById(R.id.nombrePerfil);
        TextView usuarioPerfil = (TextView)viewRoot.findViewById(R.id.usuarioPerfil);
        TextView contraseñaPerfil = (TextView)viewRoot.findViewById(R.id.contrasenaPerfil);
        Button cerrarSesion = (Button)viewRoot.findViewById(R.id.btnCerrarSesion);

        UsuarioSQLiteHelper usuarioDB=
                new UsuarioSQLiteHelper(getActivity(),"DBRestaurante",null,1);

        db=usuarioDB.getWritableDatabase();
        //Recuperamos la información pasada en el intent
        Bundle bundle = getActivity().getIntent().getExtras();

        //Construimos el mensaje a mostrar
        String idUsuario = bundle.getString("IdUsuario");
        Log.v("LUDWING IXCAYAU", idUsuario);
        Cursor c = db.rawQuery("SELECT codigo, nombre, usuario, contrasena FROM Usuario Where codigo = "+idUsuario, null);
        if(c.moveToFirst()){
            nombrePerfil.setText("Nombre: " + c.getString(1));
            usuarioPerfil.setText("Usuario: " + c.getString(2));
            contraseñaPerfil.setText("Contraseña: ************");
        }
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getActivity().getSharedPreferences("RestaurantePreferences", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.remove("idUsuario");
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return viewRoot;
    }


}
