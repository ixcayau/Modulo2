package proyecto.ludwingixcayau.modulo2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    private EditText txtUsuario;
    private EditText txtContraseña;
    private TextView crearCuenta;
    private TextView txtResultado;
    private SQLiteDatabase db;
    private Button btnContinuar;
    private Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UsuarioSQLiteHelper UsuariosDB =
                new UsuarioSQLiteHelper(this, "DBRestaurante", null, 1);

        db = UsuariosDB.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtContraseña = (EditText)findViewById(R.id.txtContraseña);
        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        btnContinuar = (Button)findViewById(R.id.btnContinuar);
        SharedPreferences settings = getSharedPreferences("RestaurantePreferences", 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            String c = settings.getString("idUsuario","");
            Bundle b = new Bundle();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            b.putString("IdUsuario", c);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT codigo,nombre,usuario, contrasena FROM Usuario Where usuario = '"+txtUsuario.getText().toString()+"' and contrasena = '"+txtContraseña.getText().toString()+"'", null);
                if(c.moveToFirst()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle b = new Bundle();
                    b.putString("IdUsuario", c.getString(0));
                    intent.putExtras(b);
                    startActivity(intent);
                    SharedPreferences settings = getSharedPreferences("RestaurantePreferences", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.putString("idUsuario", c.getString(0));
                    editor.commit();
                    Toast.makeText(getBaseContext(),"Ingreso Correcto",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"Datos incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        });
        crearCuenta = (TextView)findViewById(R.id.crearCuenta);
        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NuevaCuenta.class);
                Bundle b = new Bundle();
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Cursor c = db.rawQuery("SELECT codigo, nombre, usuario, contrasena FROM Usuario", null);

                txtResultado.setText("");
                txtResultado.append(" cod  -  nombre  -  usuario  -  contraseña \n");
                if (c.moveToFirst()) {
                    do {
                        int codigo = c.getInt(0);
                        String nombre = c.getString(1);
                        String usuario = c.getString(2);
                        String contraseña = c.getString(3);

                        txtResultado.append(" " + codigo + " - " + nombre + " - " + usuario + " - " + contraseña + "\n");
                    } while (c.moveToNext());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
