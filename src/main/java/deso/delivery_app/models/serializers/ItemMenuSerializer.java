package deso.delivery_app.models.serializers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.Bebida;
import deso.delivery_app.models.ItemMenu;
import deso.delivery_app.models.Plato;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;


public class ItemMenuSerializer implements ISerializable<ItemMenu>{
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertString(ItemMenu i) throws SQLException {
        if(i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA){
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, es_apto_celiaco, es_apto_vegano, peso, volumen, graduacion_alcoholica, es_alcoholica, es_gaseosa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,i.getNombre());
            ps.setString(2,i.getDescripcion());
            ps.setLong(3,i.getCategoria().getId());
            ps.setString(4,i.getCategoria().getTipoItem().toString());
            ps.setLong(5,i.getVendedor().getId());
            ps.setInt(6,i.aptoCeliaco()?1:0);
            ps.setInt(7,i.aptoVegano()?1:0);
            ps.setDouble(8,i.peso());
            ps.setDouble(9,((Bebida)i).getVolumen());
            ps.setDouble(10,((Bebida)i).getGraduacionAlcoholica());
            ps.setInt(11,((Bebida)i).esAlcoholica()?1:0);
            ps.setInt(12,((Bebida)i).esGaseosa()?1:0);
            return ps;
        }else{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, es_apto_celiaco, es_apto_vegano, peso) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,i.getNombre());
            ps.setString(2,i.getDescripcion());
            ps.setLong(3,i.getCategoria().getId());
            ps.setString(4,i.getCategoria().getTipoItem().toString());
            ps.setLong(5,i.getVendedor().getId());
            ps.setInt(6,i.aptoCeliaco()?1:0);
            ps.setInt(7,i.aptoVegano()?1:0);
            ps.setDouble(8,i.peso());
            return ps;
        }
    }

    @Override
    public PreparedStatement getUpdateString(ItemMenu i) throws SQLException{
        if(i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA){
            PreparedStatement ps = conn.prepareStatement("UPDATE item_menu SET nombre=?, descripcion=?, categoria_id=?, tipo=?, vendedor_id=?, es_apto_celiaco=?, es_apto_vegano=?, peso=?, volumen=?, graduacion_alcoholica=?, es_alcoholica=?, es_gaseosa=? WHERE id=?");
            ps.setString(1,i.getNombre());
            ps.setString(2,i.getDescripcion());
            ps.setLong(3,i.getCategoria().getId());
            ps.setString(4,i.getCategoria().getTipoItem().toString());
            ps.setLong(5,i.getVendedor().getId());
            ps.setInt(6,i.aptoCeliaco()?1:0);
            ps.setInt(7,i.aptoVegano()?1:0);
            ps.setDouble(8,i.peso());
            ps.setDouble(9,((Bebida)i).getVolumen());
            ps.setDouble(10,((Bebida)i).getGraduacionAlcoholica());
            ps.setInt(11,((Bebida)i).esAlcoholica()?1:0);
            ps.setInt(12,((Bebida)i).esGaseosa()?1:0);
            ps.setLong(13,i.getId());
            return ps;
        }else{
            PreparedStatement ps = conn.prepareStatement("UPDATE item_menu SET nombre=?, descripcion=?, categoria_id=?, tipo=?, vendedor_id=?, es_apto_celiaco=?, es_apto_vegano=?, peso=? WHERE id=?");
            ps.setString(1,i.getNombre());
            ps.setString(2,i.getDescripcion());
            ps.setLong(3,i.getCategoria().getId());
            ps.setString(4,i.getCategoria().getTipoItem().toString());
            ps.setLong(5,i.getVendedor().getId());
            ps.setInt(6,i.aptoCeliaco()?1:0);
            ps.setInt(7,i.aptoVegano()?1:0);
            ps.setDouble(8,i.peso());
            ps.setLong(9,i.getId());
            return ps;
        }
    }

    @Override
    public PreparedStatement getDeleteString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM item_menu WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedString(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_menu WHERE id=?");
        ps.setLong(1,id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllString() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_menu");
        return ps;
    }
}
