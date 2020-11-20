package filter;
/*	import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.emrah.demorest.LoginBase;
import org.glassfish.jersey.internal.util.Base64;



	@Provider
	public class SecurityFilter implements ContainerRequestFilter {
		TokenManager to=new TokenManager();
		LoginBase lo=new LoginBase();
		private static final String AUTHORIZATION_HEADER_KEY ="Authorization";//key
		//private static final String BEARER ="Bearer";
		private static final String AUTHORIZATION_HEADER_PREFIX ="Bearer";
		private static final String SECURED_URL_PREFIX="secured";
		@Override
		public void filter(ContainerRequestContext requestContext) throws IOException {
			if(requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
				List<String>authHeader=requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);//keyi kontrol ediyoruz
				if(authHeader != null && authHeader.size()>0)
				{
					String authToken=authHeader.get(0);
					authToken =authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
					String decodedString =Base64.decodeAsString(authToken);	
					StringTokenizer tokenizer=new StringTokenizer(decodedString, ":");
					String username = tokenizer.nextToken();
					String password =tokenizer.nextToken();
					if("username".equals(username) && "password".equals(password))
							{
						return ;
							}
				}
				Response unauthorizedStatus =Response
	 											.status(Response.Status.UNAUTHORIZED)
	 											.entity("user cannot access the resource.")
	 											.build();
	 			requestContext.abortWith(unauthorizedStatus);
			}
			
		}

	}
*/
