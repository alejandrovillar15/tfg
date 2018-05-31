package cuborn.proyecto_crm.Pedidos;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cuborn.proyecto_crm.R;

public class crea_pedido extends ArrayAdapter {

    private final Activity contexto;
    private final ArrayList<String> precios,fechas_pedidos,detalles_pedidos;
    private final ArrayList<Integer> ids_pedidos,ids_clientes,pagados,entregados,beneficios_totales;
    private final SQLiteDatabase BD;

    public crea_pedido(@NonNull Activity contexto, ArrayList<Integer> ids_pedidos, ArrayList<Integer> ids_clientes,
                       ArrayList<Integer> pagados, ArrayList<Integer> entregados, ArrayList<String> precios,
                       ArrayList<String> fechas_pedidos, ArrayList<Integer> beneficios_totales, ArrayList<String>detalles_pedidos, SQLiteDatabase BD){

        super(contexto, R.layout.fila_cliente_vista,precios);
        this.contexto=contexto;
        this.ids_pedidos=ids_pedidos;
        this.ids_clientes=ids_clientes;
        this.pagados=pagados;
        this.entregados=entregados;
        this.precios=precios;
        this.fechas_pedidos=fechas_pedidos;
        this.beneficios_totales=beneficios_totales;
        this.detalles_pedidos=detalles_pedidos;
        this.BD=BD;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {

        //Creamos el inflater
        LayoutInflater inflater = contexto.getLayoutInflater();

        //Creamos una vista con la estructra de la vista fila_cliente
        View FilaPedido = inflater.inflate(R.layout.fila_pedido_vista,null,true);

        TextView etiqueta_fecha = FilaPedido.findViewById(R.id.etiqueta_fecha);
        TextView etiqueta_cliente = FilaPedido.findViewById(R.id.etiqueta_cliente);
        CheckBox check_entregado = FilaPedido.findViewById(R.id.check_entregado);
        CheckBox check_pagado = FilaPedido.findViewById(R.id.check_pagado);
        String nombre_cliente="";

        String sentencia="SELECT NOMBRE FROM clientes WHERE ID_CLIENTE="+ids_clientes.get(position);
        Cursor cursor=BD.rawQuery(sentencia,null);
        if(cursor.getColumnCount()>0) {
            //Esto se lo salta y no entiendo xk
            while (cursor.moveToNext()) {
                nombre_cliente = cursor.getString(0);
            }
        }
        cursor.close();
        //La posicion en el ListView coincide con la posicion en los Arrays
        etiqueta_fecha.setText(fechas_pedidos.get(position));
        etiqueta_cliente.setText(nombre_cliente);
        if (entregados.get(position)==1){
            check_entregado.setChecked(true);
        }else{
            check_entregado.setChecked(false);
        }
        if (pagados.get(position)==1){
            check_pagado.setChecked(true);
        }else{
            check_pagado.setChecked(false);
        }

        //Le pasamos como etiqueta de la vista el id propio del cliente
        FilaPedido.setTag(ids_pedidos.get(position));

        return FilaPedido;
    }
}
