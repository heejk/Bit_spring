package org.hecto.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTest {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch(Exception e) { }
	}
	
	@Test
	public void testConnection() {
		try(Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@10.211.55.3:1521:xe",
				"scott",
				"tiger")) { log.info(conn); }
			
		catch(Exception e) { }
	}
}
