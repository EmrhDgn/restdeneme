 package ogrenci;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OgrenciDepolama {

	
	Connection con=null;
	public OgrenciDepolama() {
		String url ="jdbc:mysql://127.0.0.1:3306/ogrenci?\" + \"autoReconnect=true&useSSL=false";//direk kopyala yapıştır yaptım.
		String username="root";
		String password="123456";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	public List<Ogrenci> getOgrenciler()
	{
		List<Ogrenci> ogrenciler =new ArrayList<>();
		String sql= ("select * from ogrenci");//hangi sql sorgusunu yapacağını seciyoruz.
		try {
			Statement st = con.createStatement();
			ResultSet rs=st.executeQuery(sql);//sorguyu isliyoruz.
			while(rs.next()) {
				Ogrenci o=new Ogrenci();
				o.setOgr_no(rs.getInt(1));
				o.setOgr_ad(rs.getString(2));
				o.setOgr_soyad(rs.getString(3));
				ogrenciler.add(o);
			}			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return ogrenciler;
	}
	public Ogrenci getOgrenci(int ogr_no) 
	{// id siyle istedigimiz tek ogrenciyi kontrol için.adıylada çağırabiliriz.
		String sql=("select * from ogrenci where ogr_no="+ogr_no);
		Ogrenci o=new Ogrenci();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				o.setOgr_no(rs.getInt(1));
				o.setOgr_ad(rs.getString(2));
				o.setOgr_soyad(rs.getString(3));
			//	o.setSonuc(rs.getInt(4));
	}
		} catch (Exception e) {
			System.out.println(e);
		}
		return o;
	}
	public void yeni(Ogrenci o1) {
		String sql="insert into ogrenci values(?,?,?)";
		try {
			
			PreparedStatement st =con.prepareStatement(sql);
			st.setInt(1, o1.getOgr_no());
			st.setString(2, o1.getOgr_ad());//2 girilen eleman isim olarak alınıcak
			st.setString(3, o1.getOgr_soyad());
		
			st.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public void duzenle(Ogrenci o1)
	{
		String sql="update ogrenci set ogr_ad=?,ogr_soyad=? where ogr_no=?";
		try {
			PreparedStatement st =con.prepareStatement(sql);
			
			st.setString(1,o1.getOgr_ad());//2 girilen eleman isim olarak alınıcak
			st.setString(2,o1.getOgr_soyad());
			st.setInt(3,o1.getOgr_no());
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	public void sil(int ogr_no) {
		String sql="delete from ogrenci where ogr_no=?";
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
