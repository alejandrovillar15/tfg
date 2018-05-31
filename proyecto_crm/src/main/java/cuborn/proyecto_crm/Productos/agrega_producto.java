package cuborn.proyecto_crm.Productos;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cuborn.proyecto_crm.R;

public class agrega_producto extends AppCompatActivity implements View.OnClickListener {
    double precio,PVP;
    String nombre,detalles,imagen;
    EditText nombre_producto,detalles_producto,precio_producto,PVP_producto;
    SQLiteDatabase BD;
    public Button aceptar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_producto_vista);
        aceptar = (Button) findViewById(R.id.boton_aceptar);
        cancelar = (Button) findViewById(R.id.boton_cancelar);
        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        nombre_producto=findViewById(R.id.edit_nombre);
        detalles_producto=findViewById(R.id.edit_detalles);
        precio_producto=findViewById(R.id.edit_precio);
        PVP_producto=findViewById(R.id.edit_correo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_aceptar:
                bd_productos.SETproducto(nombre_producto.getText().toString(),detalles_producto.getText().toString(), Double.valueOf(precio_producto.getText().toString()).doubleValue(),
                        Double.valueOf(PVP_producto.getText().toString()).doubleValue(),null);

                Snackbar.make(v, "producto añadido con éxito", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.boton_cancelar:
                break;
        }
    }

}