package proyecto.ludwingixcayau.modulo2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private String[] opcionesMenu;
    private DrawerLayout drawerLayout;
    private ListView menuLateral;
    private CharSequence tituloSeccion;
    private CharSequence tituloApp;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frames,new MiPedido()).commit();

        opcionesMenu = new String[] {"Platos","Bebidas","Postres","Mi Pedido","Mi Perfil"};
        drawerLayout = (DrawerLayout)findViewById(R.id.layoutDrawer);
        menuLateral = (ListView)findViewById(R.id.menu_lateral);
        menuLateral.setAdapter( new ArrayAdapter<String>(
                getSupportActionBar().getThemedContext(),
                //(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) ? android.R.layout.simple_list_item_activated_1 :  android.R.layout.simple_list_item_1
                android.R.layout.simple_list_item_activated_1
                , opcionesMenu
        ));

        menuLateral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (position){
                    case 0:
                        fragmentManager.beginTransaction().replace(R.id.frames,new Platos()).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.frames,new Bebidas()).commit();
                        break;
                    case 2:
                        fragmentManager.beginTransaction().replace(R.id.frames,new Postres()).commit();
                        break;
                    case 3:
                        fragmentManager.beginTransaction().replace(R.id.frames,new MiPedido()).commit();
                        break;
                    case 4:
                        fragmentManager.beginTransaction().replace(R.id.frames,new Perfil()).commit();
                        break;
                }


                menuLateral.setItemChecked(position, true);

                tituloSeccion = opcionesMenu[position];

                getSupportActionBar().setTitle(tituloSeccion);
                try {

                    drawerLayout.closeDrawer(menuLateral);

                }catch (ClassCastException e){

                }
            }
        });

        tituloSeccion = getTitle();
        tituloApp = getTitle();

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.drawable.ic_action_action_view_headline,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(tituloSeccion);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
            public void onDrawerOpened(View view){
                getSupportActionBar().setTitle(tituloApp);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        try {

            boolean menuAbierto = drawerLayout.isDrawerOpen(menuLateral);
            if(menuAbierto){
                menu.findItem(R.id.menu_new).setVisible(false);
                menu.findItem(R.id.menu_save).setVisible(false);
            }
            else{
                menu.findItem(R.id.menu_new).setVisible(true);
                menu.findItem(R.id.menu_save).setVisible(true);
            }

        }catch (ClassCastException e){

        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.menu_new:
                Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.menu_save:
                Log.i("ActionBar", "Guardar!");
                return true;
            case R.id.action_settings:
                Log.i("ActionBar", "Preferencias");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
