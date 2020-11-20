package ogrenci;










public class Ogrenci {
	private int ogr_no;
	private String ogr_ad;
	private String ogr_soyad;
	//private int sonuc;
	
	public Ogrenci() {
		
	}
	public int getOgr_no() {
		return ogr_no;
	}
	public void setOgr_no(int ogr_no) {
		this.ogr_no = ogr_no;
	}
	public String getOgr_ad() {
		return ogr_ad;
	}
	public void setOgr_ad(String ogr_ad) {
		this.ogr_ad = ogr_ad;
	}
	public String getOgr_soyad() {
		return ogr_soyad;
	}
	public void setOgr_soyad(String ogr_soyad) {
		this.ogr_soyad = ogr_soyad;
	}
//	public int getSonuc() {
	//	return sonuc;
	//}
	//public void setSonuc(int sonuc) {
		//this.sonuc = sonuc;
	//}
	@Override
	public String toString() {
		return "Ogrenci [ogr_no=" + ogr_no + ", ogr_ad=" + ogr_ad +  "ogr_soyad=" + ogr_soyad + "]";
	}
}
