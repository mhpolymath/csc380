package tables;


import javax.swing.table.DefaultTableModel;


import java.sql.SQLException;
public interface Relation<T> {
	
	public int insert(T element) throws SQLException;
	public int delete(T element) throws SQLException;
	public int update(T oldElement,T newElement) throws SQLException;
	
	public DefaultTableModel loadTable();

}
