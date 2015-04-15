package proyecto.ludwingixcayau.modulo2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NuevaCuenta extends ActionBarActivity {

    private TextView iralinicio;

    private EditText txtNombre;
    private EditText txtUsuario;
    private EditText txtContraseña;
    private EditText txtContraseña2;
    private Button btnRegistrarse;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cuenta);

        iralinicio=(TextView)findViewById(R.id.irALogin);
        txtNombre=(EditText) findViewById(R.id.txtNombre);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtContraseña = (EditText)findViewById(R.id.txtContraseña);
        txtContraseña2 = (EditText)findViewById(R.id.txtContraseña2);
        btnRegistrarse = (Button) findViewById(R.id.btnContinuar);

        iralinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuevaCuenta.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuarioSQLiteHelper usuariosDB =
                new UsuarioSQLiteHelper(this, "DBRestaurante", null, 1);

        db = usuariosDB.getWritableDatabase();
        btnRegistrarse.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(txtNombre.getText().toString().length()>0 && txtUsuario.getText().toString().length()>0 && txtContraseña.getText().toString().length()>0 && txtContraseña2.getText().toString().length()>0){

                    if(txtContraseña2.getText().toString().equals(txtContraseña.getText().toString())){
                        Cursor c = db.rawQuery("SELECT codigo, nombre, usuario, contrasena FROM Usuario", null);
                        int codigo = 0;
                        if (c.moveToFirst()) {
                            do {
                                codigo = c.getInt(0);
                            } while(c.moveToNext());
                        }
                        int cod = codigo+1;
                        ContentValues nuevoRegistro = new ContentValues();
                        nuevoRegistro.put("codigo", cod);
                        nuevoRegistro.put("nombre", txtNombre.getText().toString());
                        nuevoRegistro.put("usuario", txtUsuario.getText().toString());
                        nuevoRegistro.put("contrasena", txtContraseña.getText().toString());
                        db.insert("Usuario", null, nuevoRegistro);
                        Toast.makeText(getBaseContext(),"Insertado correctamente",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(NuevaCuenta.this, LoginActivity.class);
                        startActivity(intent);
                        txtNombre.setText("");
                        txtUsuario.setText("");
                        txtContraseña2.setText("");
                        txtContraseña.setText("");
                        finish();
                    }else {
                        Toast.makeText(getBaseContext(),"Error, compruebe datos",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getBaseContext(),"Error, Faltan datos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nueva_cuenta, menu);
        return true;
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
