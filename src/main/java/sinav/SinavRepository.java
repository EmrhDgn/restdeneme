package sinav;


	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

	@XmlRootElement
	public class SinavRepository {
	
		//List<Alien> aliens;
		
		Connection con = null;
		
		public SinavRepository()//?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
		{//jdbc:mysql://localhost:3306/DentistProject?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey
			//?useTimezone=True&serverTimezone=UTC
			//?autoReconnect=true&useSLL=false
			//String url ="jdbc:mysql://localhost:3306/restdb?\" + \"autoReconnect=true&useSSL=false";
			//bunların hepsi farklı my connector için kullanılıyor.
			String url ="jdbc:mysql://127.0.0.1:3306/ogrenci?\" + \"autoReconnect=true&useSSL=false";
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
		public List<Sinav> getSinavlar()
		{//get'le çağırılmak için kullanılyor bütün elemanları çağırıyor
			List<Sinav> sinavlar =new ArrayList<>();
			String sql ="select * from sinav";
			try { 
				Statement st =con.createStatement();
				ResultSet rs=st.executeQuery(sql);
				while(rs.next()) {
					Sinav s =new Sinav();
					s.setOgr_no(rs.getInt(1));
					s.setSonuc(rs.getInt(2));
					s.setDers_ad(rs.getString(3));
					sinavlar.add(s);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			return sinavlar;
			}
		public Sinav getSinav(int ogr_no)
		{	// sadece istenilen id de'ki body satır çağırılıyor.
			
			String sql ="select * from sinav where ogr_no="+ogr_no;
			Sinav s =new Sinav();
		try { 
			Statement st =con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				
				s.setOgr_no(rs.getInt(1));
				s.setSonuc(rs.getInt(2));
				s.setDers_ad(rs.getString(3));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
			return s;
		}
		public void create(Sinav s1) 
		{//postta istek gönderildimi burada yeni bir body oluşuyor
			String sql="insert into sinav values(?,?,?)";
			try { 
				PreparedStatement st =con.prepareStatement(sql);
				st.setInt(1,s1.getOgr_no());//1. girilen girdi id olacak bide uniq ayarladıgımdan çakısma olamaz.
				st.setInt(2, s1.getSonuc());
				st.setString(3,s1.getDers_ad());
				st.executeUpdate();
				
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}
		public void update(Sinav s1) 
		{//postta istek gönderildimi burada yeni bir body oluşuyor
			String sql="update sinav set ders_ad=?,sonuc=? where ogr_no=?";
			try { 
				PreparedStatement st =con.prepareStatement(sql);
				
				st.setString(1,s1.getDers_ad());//2 girilen eleman isim olarak alınıcak
				st.setInt(2,s1.getSonuc());
				st.setInt(3,s1.getOgr_no());//3. girilen giri id olacak
				st.executeUpdate();
				
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}
		public void delete(int ogr_no) {
			String sql="delete from sinav where ogr_no=?";
			try { 
				PreparedStatement st =con.prepareStatement(sql);
				st.setInt(1,ogr_no);
				st.executeUpdate();
				
			} catch (Exception e)
			{
				System.out.println(e);
			}
		}
	
}
