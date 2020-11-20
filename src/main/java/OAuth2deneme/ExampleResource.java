package OAuth2deneme;
/*
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.emrah.demorest.LoginBase;
import org.emrah.demorest.LoginDatabase;


@Path("/example")
public class ExampleResource {
	LoginDatabase lo=new LoginDatabase();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String authenticateUser(Credentials credentials) {

	    String username = credentials.getUsername();
	 //   String password = credentials.getPassword();
		return username;

	    // Authenticate the user, issue a token and return a response
	}
    @GET
    @Path("savumasiz/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response myUnsecuredMethod(@PathParam("id") Long id) {
    	System.out.println("savunmasiz");
		return null;
        // This method is not annotated with @Secured
        // The authentication filter won't be executed before invoking this method
        
    }

    @DELETE
    @Secured
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response mySecuredMethod(@PathParam("id") Long id) {
		return null;
        // This method is annotated with @Secured
        // The authentication filter will be executed before invoking this method
        // The HTTP request must be performed with a valid token
        
    }
    @GET
    @Secured
    @Path("savunmali/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) 
    public LoginBase myMethod(@PathParam("id") int id, 
                             @Context SecurityContext securityContext) {
    							LoginBase l=lo.getLogin(id);
								return l;
       
    }
}*/