package deso.delivery_app.models.serializers;

import deso.delivery_app.TIPO_ITEM;
import deso.delivery_app.models.*;
import deso.delivery_app.persistence.DBConnector;
import deso.delivery_app.utils.Coordenada;

import java.sql.*;
import java.util.List;


public class ItemMenuMapper implements ISQLMapper<ItemMenu> {
    Connection conn = DBConnector.getConnection();

    @Override
    public PreparedStatement getInsertStatement(ItemMenu i) throws SQLException {
        if (i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, apto_celiaco, apto_vegano, peso, volumen, graduacion_alcoholica, alcoholica, gaseosa,precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setLong(3, i.getCategoria().getId());
            ps.setString(4, i.getCategoria().getTipoItem().toString());
            ps.setLong(5, i.getVendedor().getId());
            ps.setInt(6, i.aptoCeliaco() ? 1 : 0);
            ps.setInt(7, i.aptoVegano() ? 1 : 0);
            ps.setDouble(8, i.peso());
            ps.setDouble(9, ((Bebida) i).getVolumen());
            ps.setDouble(10, ((Bebida) i).getGraduacionAlcoholica());
            ps.setInt(11, ((Bebida) i).esAlcoholica() ? 1 : 0);
            ps.setInt(12, ((Bebida) i).esGaseosa() ? 1 : 0);
            ps.setDouble(13, i.getPrecio());
            return ps;
        } else {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO item_menu (nombre, descripcion, categoria_id, tipo, vendedor_id, apto_celiaco, apto_vegano, peso,precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setLong(3, i.getCategoria().getId());
            ps.setString(4, i.getCategoria().getTipoItem().toString());
            ps.setLong(5, i.getVendedor().getId());
            ps.setInt(6, i.aptoCeliaco() ? 1 : 0);
            ps.setInt(7, i.aptoVegano() ? 1 : 0);
            ps.setDouble(8, i.peso());
            ps.setDouble(9, i.getPrecio());
            return ps;
        }
    }

    @Override
    public PreparedStatement getUpdateStatement(ItemMenu i) throws SQLException {
        if (i.getCategoria().getTipoItem() == TIPO_ITEM.BEBIDA) {
            PreparedStatement ps = conn.prepareStatement("UPDATE item_menu SET nombre=?, descripcion=?, categoria_id=?, tipo=?, vendedor_id=?, apto_celiaco=?, apto_vegano=?, peso=?, volumen=?, graduacion_alcoholica=?, alcoholica=?, gaseosa=?,precio= ? WHERE id=?");
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setLong(3, i.getCategoria().getId());
            ps.setString(4, i.getCategoria().getTipoItem().toString());
            ps.setLong(5, i.getVendedor().getId());
            ps.setInt(6, i.aptoCeliaco() ? 1 : 0);
            ps.setInt(7, i.aptoVegano() ? 1 : 0);
            ps.setDouble(8, i.peso());
            ps.setDouble(9, ((Bebida) i).getVolumen());
            ps.setDouble(10, ((Bebida) i).getGraduacionAlcoholica());
            ps.setInt(11, ((Bebida) i).esAlcoholica() ? 1 : 0);
            ps.setInt(12, ((Bebida) i).esGaseosa() ? 1 : 0);
            ps.setDouble(13, i.getPrecio());
            ps.setLong(14, i.getId());
            return ps;
        } else {
            PreparedStatement ps = conn.prepareStatement("UPDATE item_menu SET nombre=?, descripcion=?, categoria_id=?, tipo=?, vendedor_id=?, apto_celiaco=?, apto_vegano=?, peso=?,precio=? WHERE id=?");
            ps.setString(1, i.getNombre());
            ps.setString(2, i.getDescripcion());
            ps.setLong(3, i.getCategoria().getId());
            ps.setString(4, i.getCategoria().getTipoItem().toString());
            ps.setLong(5, i.getVendedor().getId());
            ps.setInt(6, i.aptoCeliaco() ? 1 : 0);
            ps.setInt(7, i.aptoVegano() ? 1 : 0);
            ps.setDouble(8, i.peso());
            ps.setDouble(9, i.getPrecio());
            ps.setLong(10, i.getId());
            return ps;
        }
    }

    @Override
    public PreparedStatement getDeleteStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE item_menu SET deleted_at = CURRENT_TIMESTAMP WHERE id=?");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectedStatement(long id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_menu JOIN categoria ON item_menu.categoria_id = categoria.id JOIN vendedor ON item_menu.vendedor_id = vendedor.id WHERE item_menu.id=? AND deleted_at IS NULL");
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement getSelectAllStatement() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM item_menu JOIN categoria ON item_menu.categoria_id = categoria.id JOIN vendedor ON item_menu.vendedor_id = vendedor.id WHERE deleted_at IS NULL");
        return ps;
    }

    @Override
    public List<ItemMenu> deserialize(ResultSet rs) throws SQLException {
        List<ItemMenu> itemsMenu = new java.util.ArrayList<ItemMenu>();
        while (rs.next()) {
            Vendedor v = deserializeVendedor(rs);
            Categoria c = deserializeCategoria(rs);
            TIPO_ITEM tipoItem = TIPO_ITEM.valueOf(rs.getString("tipo"));
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            String descripcion = rs.getString("descripcion");
            boolean aptoVegano = rs.getInt("apto_vegano") != 0;
            boolean aptoCeliaco = rs.getInt("apto_celiaco") != 0;
            long id = rs.getLong("id");
            ItemMenu im = null;
            switch (tipoItem) {
                case BEBIDA:
                    boolean gaseosa = rs.getInt("gaseosa") != 0;
                    boolean alcoholica = rs.getInt("alcoholica") != 0;
                    double gradoAlcohol = rs.getDouble("graduacion_alcoholica");
                    double volumen = rs.getDouble("volumen");
                    Bebida b = new Bebida(nombre, descripcion, precio, volumen, gradoAlcohol, gaseosa, alcoholica);
                    b.setVendedor(v);
                    b.setCategoria(c);
                    b.setVegano(aptoVegano);
                    b.setCeliaco(aptoCeliaco);
                    b.setId(id);
                    im = b;
                    break;
                case COMIDA:
                    double peso = rs.getDouble("peso");
                    Plato com = new Plato(nombre, descripcion, precio, peso, aptoVegano, aptoCeliaco);
                    com.setCategoria(c);
                    com.setVendedor(v);
                    com.setId(id);
                    im = com;
                    break;
            }
            if(im==null){
                throw new SQLException("Invalid structure");
            }
            itemsMenu.add(im);
        }
        return itemsMenu;
    }

    private Vendedor deserializeVendedor(ResultSet rs) throws SQLException {
        long id = rs.getLong("vendedor.id");
        String nombre = rs.getString("vendedor.nombre");
        String direccion = rs.getString("vendedor.direccion");
        String cuit = rs.getString("vendedor.cuit");
        double latitud = rs.getDouble("vendedor.latitud");
        double longitud = rs.getDouble("vendedor.longitud");
        Coordenada coord = new Coordenada(latitud, longitud);
        Vendedor vendedor = new Vendedor(nombre, direccion, cuit, coord);
        vendedor.setId(id);
        return vendedor;
    }

    private Categoria deserializeCategoria(ResultSet rs) throws SQLException {
        long id = rs.getLong("categoria.id");
        String nombre = rs.getString("categoria.descripcion");
        TIPO_ITEM tipo = TIPO_ITEM.valueOf(rs.getString("categoria.tipo_item"));
        Categoria categoria = new Categoria(nombre, tipo);
        categoria.setId(id);
        return categoria;
    }
}
