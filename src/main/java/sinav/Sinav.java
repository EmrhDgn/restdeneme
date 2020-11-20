package sinav;

public class Sinav {
	private int ogr_no;
	private int sonuc;
	private String ders_ad;
	
	public Sinav() {
		
	}

	public int getOgr_no() {
		return ogr_no;
	}

	public void setOgr_no(int ogr_no) {
		this.ogr_no = ogr_no;
	}

	public int getSonuc() {
		return sonuc;
	}

	public void setSonuc(int sonuc) {
		this.sonuc = sonuc;
	}

	public String getDers_ad() {
		return ders_ad;
	}

	public void setDers_ad(String ders_ad) {
		this.ders_ad = ders_ad;
	}
	public String toString() {
		return "Sinav [ogr_no=" + ogr_no + ", sonuc=" + sonuc + ", ders_ad=" + ders_ad + "]";
	}
}
