package alien;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlienRepository 
{
	//List<Alien> aliens;
	
	Connection con = null;
	
	public AlienRepository()//?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
	{//jdbc:mysql://localhost:3306/DentistProject?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey
		//?useTimezone=True&serverTimezone=UTC
		//?autoReconnect=true&useSLL=false
		//String url ="jdbc:mysql://localhost:3306/restdb?\" + \"autoReconnect=true&useSSL=false";
		//bunların hepsi farklı my connector için kullanılıyor.
		String url ="jdbc:mysql://127.0.0.1:3306/restdb?\" + \"autoReconnect=true&useSSL=false";
		String username="root";
		String password="123456";
		try {
			//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/restdb", "root", "123456");
			Class.forName("com.mysql.jdbc.Driver");
			con =DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public List<Alien> getAliens()
	{//get'le çağırılmak için kullanılyor bütün elemanları çağırıyor
		List<Alien> aliens =new ArrayList<>();
		String sql ="select * from alien";
		try { 
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				Alien a =new Alien();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
				aliens.add(a);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return aliens;
		}
	public Alien getAlien(int id)
	{	// sadece istenilen id de'ki body satır çağırılıyor.
		
		String sql ="select * from alien where id="+id;
		Alien a =new Alien();
	try { 
		Statement st =con.createStatement();
		ResultSet rs=st.executeQuery(sql);
		if(rs.next()) {
			
			a.setId(rs.getInt(1));
			a.setName(rs.getString(2));
			a.setPoints(rs.getInt(3));
		}
	} catch (Exception e) {
		System.out.println(e);
	}
	
	
		return a;
	}
	public void create(Alien a1) 
	{//postta istek gönderildimi burada yeni bir body oluşuyor
		String sql="insert into alien values(?,?,?)";
		try { 
			PreparedStatement st =con.prepareStatement(sql);
			st.setInt(1,a1.getId());//1. girilen girdi id olacak bide uniq ayarladıgımdan çakısma olamaz.
			st.setString(2,a1.getName());
			st.setInt(3,a1.getPoints());
			st.executeUpdate();
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public void update(Alien a1) 
	{//postta istek gönderildimi burada yeni bir body oluşuyor
		String sql="update alien set name=?,points=? where id=?";
		try { 
			PreparedStatement st =con.prepareStatement(sql);
			
			st.setString(1,a1.getName());//2 girilen eleman isim olarak alınıcak
			st.setInt(2,a1.getPoints());
			st.setInt(3,a1.getId());//3. girilen giri id olacak
			st.executeUpdate();
			
		} catch (Exception e)
		{
			System.out.println(e);
		}
	}
	public void delete(int id) {
		String sql="delete from alien where id=?";
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
/*public AlienRepository()
{
	aliens=new ArrayList<>();
	Alien a1=new Alien();
	a1.setId(1);
	a1.setName("Uzayli1");
	a1.setPoints(60); 
	
	Alien a2=new Alien();
	a2.setId(2);
	a2.setName("Uzayli2");
	a2.setPoints(50); 
	aliens.add(a1);
	aliens.add(a2);
}*/ // liste database connetiondan sonra gerek kalmadı.