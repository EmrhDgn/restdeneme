package alien;

//import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("myresource")//myresource  sayfası
public class MyResource {

   
    @GET
    @Produces(MediaType.TEXT_PLAIN)//sayfa yazı olarak çağırılıyor
    public String getIt() {
        return "Got it!";
    }
    
  /* @GET
   @Path("alien")//myresource/alien sayfası
   @Consumes(MediaType.APPLICATION_JSON)//sayfa json formatında çağırılıyor
    @Produces(MediaType.APPLICATION_JSON)
    public Alien getAlien()
	{
		System.out.println("Alien çağrıldı...");
		Alien a1=new Alien();
		a1.setName("Uzaylı1");
		a1.setPoints(60); 
		return a1;
	}*/
}
