package ogrenci;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("ogrenciler")
public class OgrenciSinif {

		OgrenciDepolama depo= new OgrenciDepolama();
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Ogrenci> getOgrenciler(){
			System.out.println("gete ulaştı  ulaştı");
			return depo.getOgrenciler();
		}
		@GET
		@Path("ogrenci/{ogr_no}")// ogrenci/id  (1,2,100) den sonra girilen id'li body yi çağırmak için kullanıyoruz.
		@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	//	@Consumes(MediaType.APPLICATION_JSON)
		public Ogrenci getOgrenci(@PathParam("ogr_no")int ogr_no)//path paramla girilen idyi elimizdeki id ile konrol ederiz
		{
			
			return depo.getOgrenci(ogr_no);
		}
		
		@POST
		@Path("ogrenci")
		@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
		public Ogrenci createOgrenci(Ogrenci o1) {
			System.out.println("post islemine ulastı");
			depo.yeni(o1);
			return o1;
		}
		@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
		@Path("ogrenci")
		@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
		public Ogrenci updateOgrenci(Ogrenci o1)//id si null sa yeni eleman eşleşme varsa değişim olur.
		{
			System.out.println(o1);
			if(depo.getOgrenci(o1.getOgr_no()).getOgr_no()==0)
			{
				depo.yeni(o1);
			}else {
				{
					depo.duzenle(o1);//girdilerimizle a1 arayi oluşturuyor
				}
			}
			
			return o1;
		}
		
		
		@DELETE
		@Path("ogrenci/{ogr_no}")//gette oldugu gibi şeçtiğimiz id yi siliyoruz
		public Ogrenci deteleOgrenci(@PathParam("ogr_no") int ogr_no)
		{
			Ogrenci o=depo.getOgrenci(ogr_no);
			if(o.getOgr_no()!=0)
			{
				depo.sil(ogr_no);
			}
			return o;
		}
		
		
	//	public static void main(String[] args) {
	//}

}
