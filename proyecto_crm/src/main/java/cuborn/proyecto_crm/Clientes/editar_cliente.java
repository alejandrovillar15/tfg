package cuborn.proyecto_crm.Clientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cuborn.proyecto_crm.R;

public class editar_cliente extends AppCompatActivity implements View.OnClickListener {

    String NOMBRETABLA = "clientes";
    private int id;
    String[] datos_cliente;
    EditText edit_nombre, edit_correo, edit_telegram, edit_telefono;
    TextView etiqueta_idcliente;
    ImageView imagen_cliente;
    Button boton_guardar;
    Button boton_editar;
    Button boton_eliminar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cliente_vista);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        boton_guardar = findViewById(R.id.boton_guardar);
        boton_guardar.setOnClickListener(this);
        boton_editar = findViewById(R.id.boton_editar);
        boton_editar.setOnClickListener(this);
        boton_eliminar = findViewById(R.id.boton_eliminar);
        boton_eliminar.setOnClickListener(this);
        datos_cliente = bd_clientes.GETcliente(id);
        etiqueta_idcliente = findViewById((R.id.etiqueta_idcliente));
        edit_nombre = findViewById(R.id.edit_nombre);
        edit_telefono = findViewById(R.id.edit_telefono);
        edit_telegram = findViewById(R.id.edit_telegram);
        edit_correo = findViewById(R.id.edit_correo);
        imagen_cliente = findViewById(R.id.imagen_cliente);

        etiqueta_idcliente.setText(datos_cliente[0]);
        edit_nombre.setText(datos_cliente[1]);
        edit_telegram.setText(datos_cliente[2]);
        edit_correo.setText(datos_cliente[3]);
        edit_telefono.setText(datos_cliente[4]);
        imagen_cliente.setImageURI(null);

        edit_nombre.setFocusable(false);
        edit_nombre.setFocusableInTouchMode(false);
        edit_nombre.setClickable(false);

        edit_correo.setFocusable(false);
        edit_correo.setFocusableInTouchMode(false);
        edit_correo.setClickable(false);

        edit_telegram.setFocusable(false);
        edit_telegram.setFocusableInTouchMode(false);
        edit_telegram.setClickable(false);

        edit_telefono.setFocusable(false);
        edit_telefono.setFocusableInTouchMode(false);
        edit_telefono.setClickable(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.boton_editar:
                if (edit_correo.isClickable()){
                    edit_nombre.setFocusable(false);
                    edit_nombre.setFocusableInTouchMode(false);
                    edit_nombre.setClickable(false);

                    edit_correo.setFocusable(false);
                    edit_correo.setFocusableInTouchMode(false);
                    edit_correo.setClickable(false);

                    edit_telegram.setFocusable(false);
                    edit_telegram.setFocusableInTouchMode(false);
                    edit_telegram.setClickable(false);

                    edit_telefono.setFocusable(false);
                    edit_telefono.setFocusableInTouchMode(false);
                    edit_telefono.setClickable(false);

                    Snackbar.make(v, "Edición deshabilitada", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                else{
                    edit_nombre.setFocusable(true);
                    edit_nombre.setFocusableInTouchMode(true);
                    edit_nombre.setClickable(true);

                    edit_correo.setFocusable(true);
                    edit_correo.setFocusableInTouchMode(true);
                    edit_correo.setClickable(true);

                    edit_telegram.setFocusable(true);
                    edit_telegram.setFocusableInTouchMode(true);
                    edit_telegram.setClickable(true);

                    edit_telefono.setFocusable(true);
                    edit_telefono.setFocusableInTouchMode(true);
                    edit_telefono.setClickable(true);

                    Snackbar.make(v, "Edición habilitada", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
                break;

            case R.id.boton_guardar:

                String SQL_update_clientes = "UPDATE " + NOMBRETABLA + " SET " +
                        "NOMBRE='" + edit_nombre.getText() + "', " +
                        "TELEGRAM='" + edit_telegram.getText() + "', " +
                        "MAIL='" + edit_correo.getText() + "', " +
                        "TELEFONO='" + edit_telefono.getText() + "', " +
                        "URL_IMG='SIN IMAGEN' WHERE ID_CLIENTE="  + Integer.parseInt(etiqueta_idcliente.getText().toString());

                bd_clientes.UPDATEcliente(SQL_update_clientes);

                Snackbar.make(v, "Cambios realizados con éxito", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;

            case R.id.boton_eliminar:

                String SQL_delete_cliente = "DELETE FROM " + NOMBRETABLA +
                        " WHERE ID_CLIENTE="  + Integer.parseInt(etiqueta_idcliente.getText().toString());

                bd_clientes.UPDATEcliente(SQL_delete_cliente);

                Snackbar.make(v, "Cliente eliminado con éxito", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                boton_editar.setEnabled(false);
                boton_guardar.setEnabled(false);
                boton_eliminar.setEnabled(false);

                edit_nombre.setFocusable(false);
                edit_nombre.setFocusableInTouchMode(false);
                edit_nombre.setClickable(false);

                edit_correo.setFocusable(false);
                edit_correo.setFocusableInTouchMode(false);
                edit_correo.setClickable(false);

                edit_telegram.setFocusable(false);
                edit_telegram.setFocusableInTouchMode(false);
                edit_telegram.setClickable(false);

                edit_telefono.setFocusable(false);
                edit_telefono.setFocusableInTouchMode(false);
                edit_telefono.setClickable(false);

                break;
        }
    }
}