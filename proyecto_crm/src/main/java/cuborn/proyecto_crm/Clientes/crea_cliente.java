package cuborn.proyecto_crm.Clientes;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class crea_cliente extends ArrayAdapter {

    private final Activity contexto;
    private final ArrayList<String> nombres, telegrams, mails, telefonos, imagenes;
    private final ArrayList<Integer> ids;

    public crea_cliente(@NonNull Activity contexto, ArrayList<Integer> ids, ArrayList<String> nombres,
                        ArrayList<String> telegrams, ArrayList<String>mails, ArrayList<String>telefonos,
                        ArrayList<String>imagenes) {

        super(contexto, R.layout.fila_cliente_vista,telefonos);
        this.contexto=contexto;
        this.ids=ids;
        this.nombres=nombres;
        this.telegrams=telegrams;
        this.mails=mails;
        this.telefonos=telefonos;
        this.imagenes=imagenes;
    }

    //Ete método estaria bien entenderlo un poco mejor
    //¿Por que esos parametros de entrada? ¿Por que aqui y no en la otra clase? etc...
    //Metodo para crear cada fila del listview

    public View getView(int position, View convertView, ViewGroup viewGroup) {

        //Creamos el inflater
        LayoutInflater inflater = contexto.getLayoutInflater();

        //Creamos una vista con la estructra de la vista fila_cliente
        View FilaCliente = inflater.inflate(R.layout.fila_cliente_vista,null,true);

        TextView etiqueta_nombre = FilaCliente.findViewById(R.id.etiqueta_nombre);
        TextView etiqueta_telefono = FilaCliente.findViewById(R.id.etiqueta_telefono);
        TextView etiqueta_telegram = FilaCliente.findViewById(R.id.etiqueta_telegram);
        TextView etiqueta_mail = FilaCliente.findViewById(R.id.etiqueta_mail);
        ImageView imagen_cliente = FilaCliente.findViewById(R.id.imagen_cliente);

        //La posicion en el ListView coincide con la posicion en los Arrays
        etiqueta_nombre.setText(nombres.get(position));
        etiqueta_telefono.setText(telefonos.get(position));
        etiqueta_telegram.setText(telegrams.get(position));
        etiqueta_mail.setText(mails.get(position));
        imagen_cliente.setImageURI(null);

        //Le pasamos como etiqueta de la vista el id propio del cliente
        FilaCliente.setTag(ids.get(position));

        return FilaCliente;
    }
}
