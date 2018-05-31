package cuborn.proyecto_crm;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import cuborn.proyecto_crm.Clientes.agrega_cliente;
import cuborn.proyecto_crm.Clientes.bd_clientes;
import cuborn.proyecto_crm.Clientes.editar_cliente;
import cuborn.proyecto_crm.Pedidos.agrega_pedido;
import cuborn.proyecto_crm.Pedidos.bd_pedidos;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SQLiteDatabase BD;
    String modulo = "pedidos";
    bd_clientes control_clientes = new bd_clientes(this);
    bd_pedidos control_pedidos = new bd_pedidos(this);
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //-------- INSTANCIAMOS LA LISTA ----------//
        lista = (ListView) findViewById(R.id.lista);

        //--------- CREAMOS LA BD Y LAS TABLAS SI NO EXISTEN -----------//
        String SQL_usuarios = "CREATE TABLE USUARIOS(" +
                "ID_USUARIO INT(4) NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE VARCHAR(20))";

        String SQL_productos = "CREATE TABLE PRODUCTOS(" +
                "ID_PRODUCTO INT(4) NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE VARCHAR(20)," +
                "DETALLES VARCHAR(130)," +
                "PRECIO DOUBLE," +
                "PVP DOUBLE," +
                "URL_IMG VARCHAR(50))";

        String SQL_productos_pedidos = "CREATE TABLE  PRODUCTOS_PEDIDOS(" +
                "ID_PRODUCTO_M INT(4) NOT NULL," +
                "ID_PEDIDO_M INT(4) NOT NULL," +
                "CANTIDAD INT(6) NOT NULL, " +
                "PRIMARY KEY (ID_PRODUCTO_M, ID_PEDIDO_M), " +
                "FOREIGN KEY (ID_PRODUCTO_M) REFERENCES PRODUCTOS(ID_PRODUCTO)," +
                "FOREIGN KEY (ID_PEDIDO_M) REFERENCES PEDIDOS(ID_PEDIDO))";

        String SQL_usuarios_pedidos = "CREATE TABLE USUARIOS_PEDIDOS(" +
                "ID_USUARIO_N INT(4) NOT NULL," +
                "ID_PEDIDO_N INT(4) NOT NULL, " +
                "PRIMARY KEY (ID_USUARIO_N, ID_PEDIDO_N), " +
                "FOREIGN KEY (ID_USUARIO_N) REFERENCES USUARIOS(ID_USUARIO)," +
                "FOREIGN KEY (ID_PEDIDO_N) REFERENCES PEDIDOS(ID_PEDIDO))";


        BD=openOrCreateDatabase("MiniCRM", Context.MODE_PRIVATE,null);
        //BD.execSQL(SQL_pedidos);
        //BD.execSQL(SQL_usuarios);
        //BD.execSQL(SQL_productos);
        //BD.execSQL(SQL_productos_pedidos);
        //BD.execSQL(SQL_usuarios_pedidos);

        //Creamos un objeto bd_clientes para poder llamar a sus metodos
        bd_clientes control_clientes = new bd_clientes(this);
        bd_pedidos control_pedidos = new bd_pedidos(this);

        //Llamamos al metodo que crea la tabla si no existe
        control_clientes.creaTabla(BD);
        control_pedidos.creaTabla(BD);

        //Añadimos datos de prueba
        /*control_clientes.SETcliente(0001,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0002,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);
        control_clientes.SETcliente(0003,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0004,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);
        control_clientes.SETcliente(0005,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0006,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);
        control_clientes.SETcliente(0007,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0013,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);
        control_clientes.SETcliente(0014,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0010,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);
        control_clientes.SETcliente(0011,"Cristian","@Confeti","mr.cuevaslopez@gmail.com","629629629","Barba.jpg",BD);
        control_clientes.SETcliente(0012,"Alex","@Alex","alex@gmail.com","629625629","Seta.png",BD);*/

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = sdf.parse("21/12/2012");
            control_pedidos.SETpedido(0001,true,false,"12,25$",fecha,6,"Pedido normal");

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        SETlayout();


        //--//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        //--//
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void SETlayout(){
        switch (modulo){
            case "clientes":
                //Añadimos los datos de la tabla a las vistas (filas) del listView
                control_clientes.GETclientes(BD,lista,this);

                //Le ponemos el listener a la ListView y cada vez que se pinche se lanza un intent
                //llamando a la editar_cliente_vista y le pasamos el id y la posicion pinchada
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Intent intent = new Intent(MainActivity.this, editar_cliente.class);
                        int idCliente = (int) view.getTag();
                        intent.putExtra("id", idCliente);
                        startActivity(intent);
                    }
                });
                break;

            case "pedidos":
                control_pedidos.GETpedidos(BD,lista,this);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



///////------------- MENU SUPERIOR DERECHO ---------------////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.agregar_cliente) {

            Intent intent = new Intent(MainActivity.this, agrega_cliente.class);
            startActivity(intent);

        }else if(id == R.id.agregar_pedido){

            Intent intent = new Intent(MainActivity.this, agrega_pedido.class);
            startActivity(intent);

        }else if(id == R.id.agregar_producto){

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pedidos) {
            modulo="pedidos";
            SETlayout();

        } else if (id == R.id.productos) {

        } else if (id == R.id.clientes) {
            modulo="clientes";
            SETlayout();

        } else if (id == R.id.usuarios) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
