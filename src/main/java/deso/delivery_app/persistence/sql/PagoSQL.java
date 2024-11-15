package deso.delivery_app.persistence.sql;

import deso.delivery_app.exception.ItemNoEncontradoException;
import deso.delivery_app.models.Cliente;
import deso.delivery_app.models.Pago;
import deso.delivery_app.models.serializers.ClienteMapper;
import deso.delivery_app.models.serializers.PagoMapper;
import deso.delivery_app.persistence.DAO.PagoDAO;
import deso.delivery_app.persistence.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PagoSQL implements PagoDAO {
    PagoMapper mapper = new PagoMapper();

    @Override
    public Pago create(Pago p) {
        try {
            PreparedStatement ps = mapper.getInsertStatement(p);
            int rowsAffected = ps.executeUpdate();
            long insertId = sqlUtils.fetchId(rowsAffected, ps);
            p.setId(insertId);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pago get(long id) {
        try {
            PreparedStatement ps = mapper.getSelectedStatement(id);
            ResultSet rs = ps.executeQuery();
            Pago c = mapper.deserialize(rs).getFirst();
            if (c == null) throw new ItemNoEncontradoException("Pago no encontrado");
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Pago update(Pago p) {
        try {
            PreparedStatement ps = mapper.getUpdateStatement(p);
            ps.executeUpdate();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement ps = mapper.getDeleteStatement(id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
