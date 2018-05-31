package cuborn.proyecto_crm.Pedidos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import cuborn.proyecto_crm.R;

public class agrega_pedido extends AppCompatActivity implements View.OnClickListener {

    String precio,fecha,detalles,nombre;
    EditText edit_precio,edit_fecha,edit_detalles;
    CheckBox check_pagado,check_entregado;
    Spinner spinner_nombre;
    SQLiteDatabase BD;
    public Button aceptar, cancelar;
    ArrayList<Integer> ids_clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_pedido_vista);
        aceptar = (Button) findViewById(R.id.boton_aceptar);
        cancelar = (Button) findViewById(R.id.boton_cancelar);
        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        edit_precio=findViewById(R.id.edit_precio);
        edit_fecha=findViewById(R.id.edit_fecha);
        edit_detalles=findViewById(R.id.edit_detalles);
        spinner_nombre = (Spinner) findViewById(R.id.spinner_nombre);
        check_entregado=findViewById(R.id.check_entregado);
        check_pagado=findViewById(R.id.check_pagado);

        BD=openOrCreateDatabase("MiniCRM", Context.MODE_PRIVATE,null);
        ArrayList<String> nombres_clientes = new ArrayList<String>();
        ids_clientes = new ArrayList<Integer>();

        Cursor cursor=BD.rawQuery("SELECT NOMBRE,TELEFONO,ID_CLIENTE FROM clientes" ,null);

        if(cursor.getColumnCount()>0) {
            while(cursor.moveToNext()){
                nombres_clientes.add(cursor.getString(0)+" ("+cursor.getString(1)+")");
                ids_clientes.add(cursor.getInt(2));
            }
        }
        spinner_nombre.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombres_clientes));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_aceptar:
                bd_pedidos.SETpedido( ids_clientes.get((int)spinner_nombre.getSelectedItemId()),check_pagado.isChecked(),
                        check_entregado.isChecked(),edit_precio.getText().toString(),null,0,
                        edit_detalles.getText().toString());

                Snackbar.make(v, "Producto añadido con éxito", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.boton_cancelar:
                break;
        }
    }
}
