	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;
	
public class DB_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// adding a line to test GitHub

		Connection conn = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;

		try {
		    //conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?" + "user=root&password=root");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook","root","root"); 
		    
		    // Do something with the Connection
		    System.out.println("connection happened");
		    try {
		        stmt = conn.createStatement();
		        rs = stmt.executeQuery("SELECT * FROM addresses where addr_type=\"Business\"");
		        rs = stmt.getResultSet();
		        while (rs.next())
		        { 
		        	System.out.println(rs.getString(1)+ " " + rs.getString(2));// where the number is the column number
		        	System.out.println(rs.getString("addr_phone_2_type"));
		        }

		        // or alternatively, if you don't know ahead of time that
		        // the query will be a SELECT...

		        //if (stmt.execute("SELECT foo FROM bar")) {
		        //    rs = stmt.getResultSet();
		        //}

		        // Now do something with the ResultSet ....
		    }
		    catch (SQLException ex){
		        // handle any errors
		        System.out.println("SQLException: " + ex.getMessage());
		        System.out.println("SQLState: " + ex.getSQLState());
		        System.out.println("VendorError: " + ex.getErrorCode());
		    }
		    finally {
		        // it is a good idea to release
		        // resources in a finally{} block
		        // in reverse-order of their creation
		        // if they are no-longer needed

		        if (rs != null) {
		            try {
		                rs.close();
		            } catch (SQLException sqlEx) { } // ignore

		            rs = null;
		        }

		        if (stmt != null) {
		            try {
		                stmt.close();
		            } catch (SQLException sqlEx) { } // ignore

		            stmt = null;
		        }
		    }

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}

}
