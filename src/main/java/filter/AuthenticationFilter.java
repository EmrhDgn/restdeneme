package filter;


import java.util.Base64;
import java.util.Date;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.emrah.demorest.LoginBase;

import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	String a;
	LoginBase l1=new LoginBase();
	TokenManager t=new TokenManager();
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
    	
        // Get the Authorization header from the request
    	
    	String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    	//String authorizationHeader =t.Token(l1);	  
    	if(authorizationHeader.startsWith("Bearer ")) {
    		 authorizationHeader = authorizationHeader.substring(7, authorizationHeader.length());
    	}
        String username=getAllClaimsFromToken(authorizationHeader).toString();      
    //	String username=getUsernameToken(authorizationHeader).toString();
    	System.out.println(username);
        System.out.println(authorizationHeader);
       /* if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }*/
        //System.out.println(t.Token(l1));
    	//	System.out.println(authorizationHeader);
		//	String k1=t.tokenKontrol(authorizationHeader, l1);
		//	System.out.println(k1);
			//System.out.println(k1);
			/*		l1.setUuid("2374f61d-1c3c-4b38-8c10-3aa2cc41c66c");
			String k2=t.tokenKontrol(authorizationHeader, l1);
		if (k1==k2) {
			return ;
		}else {
			abortWithUnauthorized(requestContext);
		}
    /*	l1.setUuid("b3ebc70a-61d9-4042-9b23-54asaa6f7037f");
    	try {
			t.tokenKontrol(authorizationHeader, l1);
		} catch (Exception e) {
			  Response.status(Response.Status.UNAUTHORIZED)
              .header("WWW_AUTHENTICATE", AUTHENTICATION_SCHEME)
              .build();
		}*/
        	if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        
        // Extract the token from the Authorization header
        String token =authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        System.out.println(token);
        try {
        	
            validateToken(token);
        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // Authentication scheme comparison must be case-insensitive
        return (authorizationHeader != null &&
            authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " "));
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code
        // The "WWW-Authenticate" is sent along with the response
    		requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                    .header("WWW_AUTHENTICATE", AUTHENTICATION_SCHEME)
                    .build());
    	 /*requestContext.abortWith(
                 Response.status(Response.Status.UNAUTHORIZED)
                         .header("WWW_AUTHENTICATE", 
                                 AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                         .build());*/
    }

    private boolean validateToken(String token) throws Exception {
    	if(getUsernameToken(token) !=null && isExpired(token)) {
			
			return true;
		}
		return false;
	}
	public String getUsernameToken(String token) {
		Claims claims  = getClaims(token);
		return claims.getAudience();
		//return claims.getSubject();
	}
	public boolean isExpired(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().before(new Date(System.currentTimeMillis()));
	}
	private Claims getClaims(String token) {
		byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
		Claims claims= Jwts.parser()
				.requireAudience(l1.getUuid())
				.setSigningKey(Keys.hmacShaKeyFor(secret))
				.parseClaimsJws(token)
				.getBody();
		return claims;
	}
	private Claims getAllClaimsFromToken(String token) {
	    Claims claims;
	    try {
	    	byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
	        claims = Jwts.parser()
	        		.setSigningKey(Keys.hmacShaKeyFor(secret))
	                .parseClaimsJws(token)
	                .getBody();
	                
	    } catch (Exception e) {
	    	System.out.println(e);
	  //      LOGGER.error("Could not get all claims Token from passed token");
	        claims = null;
	    }
	    return claims;
	}
        // Check if it was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
}
