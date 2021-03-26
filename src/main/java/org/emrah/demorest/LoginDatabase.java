package org.emrah.demorest;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;




	public class LoginDatabase {
		Connection con=null;
		public LoginDatabase() {			
			String url ="jdbc:mysql://localhost:3306/user?\" + \"autoReconnect=true&useSSL=false";
			String username="root";
			String password="123456";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con =DriverManager.getConnection(url,username,password);
			} catch (Exception e) {
				System.out.println(e);
			}
			}
		
		public List<LoginBase> getLogins()
			{
			List<LoginBase> logins =new ArrayList<>();
			String sql= ("select * from login");//hangi sql sorgusunu yapacağını seciyoruz.
			try {
				Statement st = con.createStatement();
				ResultSet rs=st.executeQuery(sql);//sorguyu isliyoruz.
				while(rs.next()) {
					LoginBase l=new LoginBase();
					l.setId(rs.getInt(1));
					l.setUsername(rs.getString(2));
					l.setPassword(rs.getString(3));
					l.setRole(rs.getString(4));
					logins.add(l);
						}			
					} catch (Exception e) {
						System.out.println(e);
					}
					
					return logins;
				}
	
		
		public LoginBase getLogin(int id) {
			
			String sql =("select * from login where id="+id);
			LoginBase l =new LoginBase();
			try {
				Statement st =con.createStatement();
				ResultSet rs=st.executeQuery(sql);
				if (rs.next())
				{
					l.setId(rs.getInt(1));
					l.setUsername(rs.getString(2));
					l.setPassword(rs.getString(3));
					l.setRole(rs.getString(4));
					
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			return l;
		}
			
		public void yeni(LoginBase l1)
		{
		
			String sql="insert into login values(?,?,?,?,?)";
			try {
				PreparedStatement st =con.prepareStatement(sql);
				st.setInt(1, l1.getId());
				st.setString(2, l1.getUsername());
				st.setString(3, l1.getPassword());
				st.setString(4, l1.getRole());
				UUID uuid = UUID.randomUUID();
				String u=uuid.toString();
				l1.setUuid(u);
				st.setString(5, l1.getUuid());
				st.executeUpdate();
				
				
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

		public LoginBase getDuzenle(LoginBase l1) {
			String sql="update login set password=? where id=?";
			try {
				PreparedStatement st1=con.prepareStatement(sql);
				st1.setString(1, l1.getPassword());
				st1.setInt(2, l1.getId());
				st1.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
			return l1;
		}
		
		public void duzenle(LoginBase l1)
		{
			String sql="update login set username=?, password=? ,role=? ,uuid=? where id=?";
			try {
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1, l1.getUsername());
				st.setString(2, l1.getPassword());
				st.setString(3, l1.getRole());
				st.setString(4, l1.getUuid());
				st.setInt(5, l1.getId());
				st.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		public List<LoginBase> duzenlemeler() {		
	//	public void sifreduzenle(LoginBase l1) {
			List<LoginBase> duzens =new ArrayList<>();
			String a;
			String sql= ("select * from login");//hangi sql sorgusunu yapacağını seciyoruz.
			try {
				PreparedStatement st =con.prepareStatement(sql);
			//	Statement st = con.createStatement();
				ResultSet rs=st.executeQuery(sql);//sorguyu isliyoruz.
				while(rs.next()) {
					LoginBase l=new LoginBase();
					a=l.getPassword();
					String veri = BCrypt.hashpw(a, BCrypt.gensalt());
				    l.setPassword(veri);
					st.setString(1, l.getPassword());
					st.setInt(2, l.getId());
					st.executeUpdate();
					
					a=l.getPassword();
					l.setId(rs.getInt(1));
					l.setUsername(rs.getString(2));
					l.setPassword(rs.getString(3));
					l.setRole(rs.getString(4));
					
					duzens.add(l);
						}			
					} catch (Exception e) {
						System.out.println(e);
					}
					
					return duzens;
				}
			
		/*	String sql="update login set password=? where id=?";
			try {
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1, l1.getPassword());
				st.setInt(2, l1.getId());
				st.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}	*/
		public void uuidsizduzenle(LoginBase l1)
		{
			String sql="update login set username=?, password=? ,role=? where id=?";
			try {
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1, l1.getUsername());
				st.setString(2, l1.getPassword());
				st.setString(3, l1.getRole());
				st.setInt(4, l1.getId());
				st.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		public void uuidduzenle(LoginBase l1)
		{
			String sql="update login set username=?, password=? ,role=? ,uuid=? where id=?";
			try {
				PreparedStatement st =con.prepareStatement(sql);
				st.setString(1, l1.getUsername());
				st.setString(2, l1.getPassword());
				st.setString(3, l1.getRole());
				UUID uuid = UUID.randomUUID();
				String u=uuid.toString();
				l1.setUuid(u);
				st.setString(4, l1.getUuid());
				st.setInt(5, l1.getId());
				st.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
		public void sil(int id) {
			String sql="delete from login where id=?";
			try { 
				PreparedStatement st =con.prepareStatement(sql);
				st.setInt(1,id);
				st.executeUpdate();
				
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}
		
	}
















/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginDatabase {
	Connection con=null;
	public LoginDatabase() {			
		String url ="jdbc:mysql://127.0.0.1:3306/user?\" + \"autoReconnect=true&useSSL=false";
		String username="root";
		String password="123456";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.println(e);
		}
		}
	public List<Login> getLogins()
		{
		List<Login> logins =new ArrayList<>();
		String sql= ("select * from login");//hangi sql sorgusunu yapacağını seciyoruz.
		try {
			Statement st = con.createStatement();
			ResultSet rs=st.executeQuery(sql);//sorguyu isliyoruz.
			while(rs.next()) {
				Login l=new Login();
			/*	l.setId(rs.getInt(1));
				l.setF_name(rs.getString(2));
				l.setL_name(rs.getString(3));*//*
				l.setUsername(rs.getString(1));
				l.setPassword(rs.getString(2));
				
				logins.add(l);
					}			
				} catch (Exception e) {
					System.out.println(e);
				}
				
				return logins;
			}
	public Login getLogin(String username) {
		Login l =new Login();
		String sql =("select * from login where username="+username);
		try {
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if (rs.next())
			{
				/*l.setId(rs.getInt(1));
				l.setF_name(rs.getString(2));
				l.setL_name(rs.getString(3));*//*
				l.setUsername(rs.getString(1));
				l.setPassword(rs.getString(2));
				
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return l;
	}
	public void yeni(Login l1)
	{
		String sql="insert into login values(?,?)";
		try {
			PreparedStatement st =con.prepareStatement(sql);
			//st.setInt(1, l1.getId());
			//st.setString(2, l1.getF_name());//2 girilen eleman isim olarak alınıcak
			//st.setString(3, l1.getL_name());
			st.setString(1, l1.getUsername());
			st.setString(2, l1.getPassword());
			st.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public void duzenle(Login l1)
	{
		String sql="update login set password=? where username=?";
		try {
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1, l1.getUsername());
			st.setString(2, l1.getPassword());
			
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public void sil(String username) {
		String sql="delete from ogrenci where username=?";
		try { 
			PreparedStatement st =con.prepareStatement(sql);
			st.setString(1,username);
			st.executeUpdate();
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
	



}
	*/
	
	
	



