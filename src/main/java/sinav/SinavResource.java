package sinav;


	
	//import java.util.Arrays;
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
	import javax.xml.bind.annotation.XmlRootElement;
	@XmlRootElement
	@Path("sinavlar")// my resourse yerine alien yazarak alien sayfasını çağırıyoruz

	public class SinavResource {
	
		SinavRepository pepo= new SinavRepository();// repo adında alienRepository sinifından oluşmuş bir değişken üretildi.
		@GET
		@Produces(MediaType.APPLICATION_JSON)//xml yada json formattını çalıştırmak için(çevirmek)
		public List<Sinav> getSinavlar()
		{
			System.out.println("sivas geti çagırdı çağrıldı...");	
			//List<Alien> aliens=Arrays.asList(a1,a2);//burada listeliyip.listeyi returnla listeyi çağırıyoruz
			return pepo.getSinavlar();
		}
		@GET
		@Path("sinav/{ogr_no}")// alien/id  (1,2,100) den sonra girilen id'li body yi çağırmak için kullanıyoruz.
		@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
		public Sinav getSinav(@PathParam("ogr_no")int ogr_no)//path paramla girilen idyi elimizdeki id ile konrol ederiz
		{
			return pepo.getSinav(ogr_no);
		}
		
		@POST//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
		@Path("sinav")
		@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
		public Sinav creatSinav(Sinav s1)
		{
			System.out.println(s1);
			pepo.create(s1);//girdilerimizle a1 arayi oluşturuyor
			return s1;
		}
		@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
		@Path("sinav")
		@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
		public Sinav updateSinav(Sinav s1)//id si null sa yeni eleman eşleşme varsa değişim olur.
		{
			System.out.println(s1);
			if(pepo.getSinav(s1.getOgr_no()).getOgr_no()==0)
			{
				pepo.create(s1);
			}else {
				{
					pepo.update(s1);//girdilerimizle a1 arayi oluşturuyor
				}
			}
			
			return s1;
		}
		@DELETE
		@Path("sinav/{ogr_no}")//gette oldugu gibi şeçtiğimiz id yi siliyoruz
		public Sinav deleteSinav(@PathParam("ogr_no") int ogr_no)
		{
			Sinav s=pepo.getSinav(ogr_no);
			if(s.getOgr_no()!=0)
			{
				pepo.delete(ogr_no);
			}
			return s;
		}
	}


