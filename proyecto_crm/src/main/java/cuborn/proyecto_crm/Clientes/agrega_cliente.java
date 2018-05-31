package cuborn.proyecto_crm.Clientes;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cuborn.proyecto_crm.R;

public class agrega_cliente extends AppCompatActivity implements View.OnClickListener {

    String nombre,telefono,telegram,imagen,mail;
    EditText nombre_cliente,telefono_cliente,telegram_cliente,mail_cliente;
    SQLiteDatabase BD;
    public Button aceptar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_cliente_vista);
        aceptar = (Button) findViewById(R.id.boton_aceptar);
        cancelar = (Button) findViewById(R.id.boton_cancelar);
        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        nombre_cliente=findViewById(R.id.edit_nombre);
        telefono_cliente=findViewById(R.id.edit_telefono);
        telegram_cliente=findViewById(R.id.edit_telegram);
        mail_cliente=findViewById(R.id.edit_correo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boton_aceptar:
                bd_clientes.SETcliente(nombre_cliente.getText().toString(), telegram_cliente.getText().toString(),
                        mail_cliente.getText().toString(),telefono_cliente.getText().toString(),null);

                Snackbar.make(v, "Cliente añadido con éxito", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;
            case R.id.boton_cancelar:
                break;
        }
    }

}