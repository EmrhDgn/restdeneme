package alien;

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
@Path("aliens")// my resourse yerine alien yazarak alien sayfasını çağırıyoruz
public class AlienResource 
{
	AlienRepository repo= new AlienRepository();// repo adında alienRepository sinifından oluşmuş bir değişken üretildi.
	@GET
	@Produces(MediaType.APPLICATION_JSON)//xml yada json formattını çalıştırmak için(çevirmek)
	public List<Alien> getAliens()
	{
		System.out.println("Alien çağrıldı...");	
		//List<Alien> aliens=Arrays.asList(a1,a2);//burada listeliyip.listeyi returnla listeyi çağırıyoruz
		return repo.getAliens();
	}
	@GET
	@Path("alien/{id}")// alien/id  (1,2,100) den sonra girilen id'li body yi çağırmak için kullanıyoruz.
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Alien getAlien(@PathParam("id")int id)//path paramla girilen idyi elimizdeki id ile konrol ederiz
	{
		return repo.getAlien(id);
	}
	
	@POST//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
	@Path("alien")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Alien creatAlien(Alien a1)
	{
		System.out.println(a1);
		repo.create(a1);//girdilerimizle a1 arayi oluşturuyor
		return a1;
	}
	@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
	@Path("alien")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Alien updateAlien(Alien a1)//id si null sa yeni eleman eşleşme varsa değişim olur.
	{
		System.out.println(a1);
		if(repo.getAlien(a1.getId()).getId()==0)
		{
			repo.create(a1);
		}else {
			{
				repo.update(a1);//girdilerimizle a1 arayi oluşturuyor
			}
		}
		
		return a1;
	}
	@DELETE
	@Path("alien/{id}")//gette oldugu gibi şeçtiğimiz id yi siliyoruz
	public Alien killAlien(@PathParam("id") int id)
	{
		Alien a=repo.getAlien(id);
		if(a.getId()!=0)
		{
			repo.delete(id);
		}
		return a;
	}
}
