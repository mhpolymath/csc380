package Tables;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.util.List;
public interface Relation<T> {
	
	public void insert(T element);
	public void delete(T element);
	public void update(T oldElement,T newElement);
	
	public DefaultTableModel loadTable();

}
