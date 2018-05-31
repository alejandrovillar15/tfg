package cuborn.proyecto_crm.Clientes;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class bd_clientes extends SQLiteOpenHelper {

    private static final String NOMBREBD = "MiniCRM";
    private static final String NOMBRETABLA  = "clientes";
    private static SQLiteDatabase BD;

    private static final String SQL_clientes = "CREATE TABLE IF NOT EXISTS "+ NOMBRETABLA +"( " +
                        "ID_CLIENTE INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                        "NOMBRE VARCHAR(20) NOT NULL," +
                        "TELEGRAM VARCHAR(20)," +
                        "MAIL  VARCHAR(20)," +
                        "TELEFONO VARCHAR(20)," +
                        "URL_IMG VARCHAR(50));";


    public bd_clientes(Context context){ super(context, NOMBREBD, null, 1); }

    public void creaTabla(SQLiteDatabase BD){
        Log.i("SENTENCIA",SQL_clientes);
        //BD.execSQL("DROP TABLE clientes");
        BD.execSQL(SQL_clientes);
        this.BD=BD;
    }

    public static void SETcliente(String nombre, String telegram, String mail, String telefono, String imagen){

        int id=1;
        String SQL_max = "SELECT MAX(ID_CLIENTE) FROM " + NOMBRETABLA;
        Cursor cursor = BD.rawQuery(SQL_max,null);
        if(cursor.getColumnCount()>0) {
            while(cursor.moveToNext()){
                id=(cursor.getInt(0))+1;
            }
        }

        String SQLinsert =
                "INSERT INTO " + NOMBRETABLA + " VALUES("
                        + id + ",'"
                        + nombre + "','"
                        + telegram + "','"
                        + mail + "','"
                        + telefono + "','"
                        + imagen + "');";
        Log.i("Insert_cliente --> ",SQLinsert);
        try{
            BD.execSQL(SQLinsert);
        }catch (Exception e){
            Log.e("CP repetida","Clave primaria repetida");
        }
    }

    public void GETclientes(SQLiteDatabase db, ListView lista, Activity actividad){

        //------- RECOGEMOS TODOS LOS DATOS DE TODOS LOS CLIENTES ALMACENADOS EN LA TABLA ----------//
        String sentencia="SELECT * FROM " + NOMBRETABLA + "ORDER BY id ASC";
        Cursor cursor=db.rawQuery("SELECT * FROM " + NOMBRETABLA,null);

        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<String> nombres = new ArrayList<String>();
        ArrayList<String> telegrams = new ArrayList<String>();
        ArrayList<String> mails = new ArrayList<String>();
        ArrayList<String> telefonos = new ArrayList<String>();
        ArrayList<String> imagenes = new ArrayList<String>();

        if(cursor.getColumnCount()>0) {
            while(cursor.moveToNext()){
                Log.i("REGISTROS_ID",""+cursor.getInt(0)); Log.i("REGISTROS_NOMBRE",""+cursor.getString(1)); Log.i("REGISTROS_TELEGRAM",""+cursor.getString(2)); Log.i("REGISTROS_MAIL",""+cursor.getString(3)); Log.i("REGISTROS_TELEFONO",""+cursor.getString(4)); Log.i("REGISTROS_IMAGEN",""+cursor.getString(5));
                ids.add(cursor.getInt(0));
                nombres.add(cursor.getString(1));
                telegrams.add(cursor.getString(2));
                mails.add(cursor.getString(3));
                telefonos.add(cursor.getString(4));
                imagenes.add(cursor.getString(5));
            }
        }
            cursor.close();

            //Explicar esto un poquito mejor no estaria mal
            crea_cliente adaptador = new crea_cliente(actividad, ids, nombres, telegrams, mails, telefonos, imagenes);
            lista.setAdapter(adaptador);
    }

    public static String[] GETcliente(int id){
        String[] datos = new String[6];
        Cursor c = BD.rawQuery("SELECT * FROM "+NOMBRETABLA+"  WHERE ID_CLIENTE = "+id, null);
        if(c.getColumnCount()==0){
            Log.i("Error","Select no devuelve ningun registro");
        }else{
            c.moveToNext();
            datos[0]=(c.getInt(0))+""; //id
            datos[1]=c.getString(1); //nombre
            datos[2]=c.getString(2); //telegram
            datos[3]=c.getString(3); //mail
            datos[4]=c.getString(4); //telefono
            datos[5]=c.getString(5); //imagen

            c.close();
        }
        return datos;
    }

    public static void UPDATEcliente(String SQLupdate){
        BD.execSQL(SQLupdate);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }


}
