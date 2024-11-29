package Tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import DB.DatabaseOperation;
import Entities.Member;

public class MemberTable implements Relation<Member>{

	@Override
	public void insert(Member element) {
		Connection connection = null;
		try {
			connection = DatabaseOperation.getConnection();
			String query = "INSERT INTO Member(M_id,M_Fname,M_Lname,M_phone) VALUES (?,?,?,?)";
			
		}catch(SQLException e) {
			DatabaseOperation.showErrorMessage(e.getMessage());
		}
		
		finally {
			
			DatabaseOperation.closeConnection(connection);
		}
		
	}

	@Override
	public void delete(Member element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Member oldElement, Member newElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Member> select(Member element, String criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTableModel loadTable() {
		// TODO Auto-generated method stub
		return null;
	}

}
