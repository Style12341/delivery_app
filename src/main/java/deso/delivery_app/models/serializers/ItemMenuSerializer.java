package deso.delivery_app.models.serializers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.Bebida;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Plato;

import java.util.Locale;


public class ItemMenuSerializer implements ISerializable<ItemMenu>{
    @Override
    public String getInsertString(ItemMenu i) {
        if(i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA){
            return String.format(Locale.US,"INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, es_apto_celiaco, es_apto_vegano, peso, volumen, graduacion_alcoholica, es_alcoholica, es_gaseosa) VALUES ('%s', '%s', %d, '%s', %d, %d, %d, %f, %f, %f, %d, %d)",
                    i.getNombre(), i.getDescripcion(), i.getCategoria().getId() ,i.getCategoria().getTipoItem().toString(), i.getVendedor().getId(), i.aptoCeliaco()?1:0, i.aptoVegano()?1:0, i.peso(), ((Bebida)i).getVolumen(), ((Bebida)i).getGraduacionAlcoholica(), ((Bebida)i).esAlcoholica()?1:0, ((Bebida)i).esGaseosa()?1:0);
        }else{
            return String.format(Locale.US,"INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, es_apto_celiaco, es_apto_vegano, peso) VALUES ('%s', '%s', %d, '%s', %d, %d, %d, %f)",
                    i.getNombre(), i.getDescripcion(), i.getCategoria().getId(), i.getCategoria().getTipoItem().toString(), i.getVendedor().getId(), i.aptoCeliaco()?1:0, i.aptoVegano()?1:0, i.peso());
        }
    }

    @Override
    public String getUpdateString(ItemMenu i) {

        if(i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA){
            return String.format(Locale.US,"UPDATE item_menu SET nombre='%s', descripcion='%s', categoria_id=%d, tipo='%s', vendedor_id=%d, es_apto_celiaco=%d, es_apto_vegano=%d, peso=%f, volumen=%f, graduacion_alcoholica=%f, es_alcoholica=%d, es_gaseosa=%d WHERE id=%d",
                    i.getNombre(), i.getDescripcion(), i.getCategoria().getId(), i.getCategoria().getTipoItem().toString(), i.getVendedor().getId(), i.aptoCeliaco()?1:0, i.aptoVegano()?1:0, i.peso(), ((Bebida)i).getVolumen(), ((Bebida)i).getGraduacionAlcoholica(), ((Bebida)i).esAlcoholica()?1:0, ((Bebida)i).esGaseosa()?1:0, i.getId());
        }else{
            return String.format(Locale.US,"UPDATE item_menu SET nombre='%s', descripcion='%s', categoria_id=%d, tipo='%s', vendedor_id=%d, es_apto_celiaco=%d, es_apto_vegano=%d, peso=%f WHERE id=%d",
                    i.getNombre(), i.getDescripcion(), i.getCategoria().getId(), i.getCategoria().getTipoItem().toString(), i.getVendedor().getId(), i.aptoCeliaco()?1:0, i.aptoVegano()?1:0, i.peso(), i.getId());
        }
    }

    @Override
    public String getDeleteString(long id) {
        return String.format("DELETE FROM item_menu WHERE id=%d", id);
    }

    @Override
    public String getSelectedString(long id) {
        return String.format("SELECT * FROM item_menu WHERE id=%d", id);
    }

    @Override
    public String getSelectAllString() {
        return "SELECT * FROM item_menu";
    }
}
