package cuborn.proyecto_crm.Pedidos;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class bd_pedidos extends SQLiteOpenHelper {

    private static final String NOMBREBD = "MiniCRM";
    private static final String NOMBRETABLA  = "pedidos";
    private static SQLiteDatabase BD;

    private static final String SQL_pedidos = "CREATE TABLE IF NOT EXISTS " + NOMBRETABLA + "("+
            "ID_PEDIDO INTEGER NOT NULL,"+
            "ID_CLIENTE INTEGER NOT NULL,"+
            "PAGADO BOOLEAN,"+
            "ENTREGADO BOOLEAN,"+
            "PRECIO VARCHAR(7),"+
            "FECHA_PEDIDO DATE,"+
            "BENEFICIO_TOTAL VARCHAR(7),"+
            "DETALLES VARCHAR(130),"+
            "PRIMARY KEY (ID_PEDIDO),"+
            "FOREIGN KEY (ID_CLIENTE) REFERENCES  CLIENTES(ID_CLIENTE));";

    public bd_pedidos(Context context){ super(context, NOMBREBD, null, 1); }

    public void creaTabla(SQLiteDatabase BD){
        Log.i("SENTENCIA",SQL_pedidos);
        //BD.execSQL("DROP TABLE pedidos");
        BD.execSQL(SQL_pedidos);
        this.BD=BD;
    }

    public static void SETpedido(int id_cliente, boolean pagado, boolean entregado, String precio,
                                 Date fecha_pedido, int beneficio_total,String detalles){

        int id_pedido=1;
        String SQL_max = "SELECT MAX(ID_PEDIDO) FROM " + NOMBRETABLA;
        Cursor cursor = BD.rawQuery(SQL_max,null);
        if(cursor.getColumnCount()>0) {
            while(cursor.moveToNext()){
                id_pedido=(cursor.getInt(0))+1;
            }
        }

        int entregados=0, pagados=0;
        if(entregado==true){
            entregados=1;
        }
        if(pagado==true){
            pagados=1;
        }
        String SQLinsert =
                "INSERT INTO " + NOMBRETABLA + " VALUES("
                        + id_pedido + ",'"
                        + id_cliente + "','"
                        + pagados + "','"
                        + entregados + "','"
                        + precio + "','"
                        + fecha_pedido + "','"
                        + beneficio_total + "','"
                        + detalles + "');";
        Log.i("Insert_pedido --> ",SQLinsert);
        try{
            BD.execSQL(SQLinsert);
        }catch (Exception e){
            Log.e("CP repetida","Clave primaria repetida");
        }
    }

    public void GETpedidos(SQLiteDatabase db, ListView lista, Activity actividad){

        //------- RECOGEMOS TODOS LOS DATOS DE TODOS LOS PEDIDOS ALMACENADOS EN LA TABLA ----------//
        Cursor cursor=db.rawQuery("SELECT * FROM " + NOMBRETABLA,null);

        ArrayList<Integer> ids_pedidos = new ArrayList<Integer>();
        ArrayList<Integer> ids_clientes = new ArrayList<Integer>();
        ArrayList<Integer> pagados = new ArrayList<Integer>();
        ArrayList<Integer> entregados = new ArrayList<Integer>();
        ArrayList<String> precios = new ArrayList<String>();
        ArrayList<String> fechas_pedidos = new ArrayList<String>();
        ArrayList<Integer> beneficios_totales = new ArrayList<Integer>();
        ArrayList<String> detalles_pedidos = new ArrayList<String>();

        if(cursor.getColumnCount()>0) {
            while(cursor.moveToNext()){
                ids_pedidos.add(cursor.getInt(0));
                ids_clientes.add(cursor.getInt(1));
                pagados.add(cursor.getInt(2));
                entregados.add(cursor.getInt(3));
                precios.add(cursor.getString(4));
                fechas_pedidos.add(cursor.getString(5));
                beneficios_totales.add(cursor.getInt(6));
                detalles_pedidos.add(cursor.getString(7));
            }
        }
        cursor.close();

        //Explicar esto un poquito mejor no estaria mal
        crea_pedido adaptador = new crea_pedido(actividad, ids_pedidos, ids_clientes, pagados, entregados,
                precios, fechas_pedidos, beneficios_totales,detalles_pedidos,BD);
        lista.setAdapter(adaptador);
    }

    public static String[] GETpedido(int id_pedido){
        String[] datos = new String[8];
        Cursor c = BD.rawQuery("SELECT * FROM "+NOMBRETABLA+"  WHERE ID_PEDIDO = "+id_pedido, null);
        if(c.getColumnCount()==0){
            Log.i("Error","Select no devuelve ningun registro");
        }else{
            c.moveToNext();
            datos[0]=(c.getInt(0))+""; //id_pedido
            datos[1]=(c.getInt(1))+""; //id_cliente
            datos[2]=(c.getInt(2))+""; //pagado
            datos[3]=(c.getInt(3))+""; //entregado
            datos[4]=c.getString(4); //precio
            datos[5]=c.getString(5); //fecha
            datos[6]=(c.getInt(6))+""; //beneficio_total
            datos[7]=c.getString(7);//detalles

            c.close();
        }
        return datos;
    }

    public static void UPDATEpedidos(String SQLupdate){
        BD.execSQL(SQLupdate);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
