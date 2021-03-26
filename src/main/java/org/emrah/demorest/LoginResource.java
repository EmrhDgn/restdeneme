package org.emrah.demorest;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;


import com.sun.org.apache.bcel.internal.generic.NEW;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Path("logins")
public class LoginResource {//extends AuthenticationFilter{
	LoginDatabase lo= new LoginDatabase();
	private static final Logger LOG= LogManager.getLogger(LoginResource.class);
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoginBase> getLogins(){
		System.out.println("gete ulaştı  ulaştı");
		LOG.info("logins'e girdi");
		return lo.getLogins();
	}
	@POST
	@Path("sifre")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase sifrelerikontrol(LoginBase l1)
	{
	
		if(lo.getLogin(l1.getId()).getId()==0)
		{
			lo.yeni(l1);
		}else {
			{
				
				lo.duzenlemeler();
			}
		}
		
		return l1;
	}
	
	@GET
	@Path("login/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBase getLogin(@PathParam("id")int id) {
		
		return lo.getLogin(id);
		
	}

	@POST
	@Path("jwt")
	//@Secured
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase jwt(LoginBase l1) {
		Instant now = Instant.now(); 
		byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
		String jwt=Jwts.builder()
			.setSubject(l1.getUsername())
			.setAudience(l1.getUuid())
			.claim("uuid", l1.getUuid())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.minus(2, ChronoUnit.MINUTES)))
			.signWith(Keys.hmacShaKeyFor(secret))
			.compact();	
		System.out.println(jwt);
			  try {
		    	   Jws<Claims> result= Jwts.parser()
		       			.requireAudience(l1.getUuid())//eşleşme kontrol
		       			.setAllowedClockSkewSeconds(122)
		       			.setSigningKey(Keys.hmacShaKeyFor(secret))
		       			.parseClaimsJws(jwt);
		    	   System.out.println(result);
		    	   String u=result.getBody().getAudience();
		    	   System.out.println("uuid " +u);
			} catch (Exception e) {
			  	System.out.println("unauthorized " + e );	
		    	System.out.println("token yanlis yada süresi dolmus");
			}
				return l1; 
		    }
	
	@PUT
	@Path("sifre")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase sifrekontrol(LoginBase l1)
	{
		String plaintext="yok";
		if(lo.getLogin(l1.getId()).getId()==0)
		{
			lo.yeni(l1);
		}else {
			{
				String data=l1.getPassword();
				String veri = BCrypt.hashpw(data, BCrypt.gensalt());
			    l1.setPassword(veri);
				String hashed=l1.getPassword();
				boolean bool=BCrypt.checkpw(plaintext, hashed);
				if(bool==true) {
					System.out.println("sifre dogru");
					LOG.info("Sifre dogru");
					}else {
					System.out.println("sifre yanlıs");
					LOG.error("Sifre yanlıs");
					}
				lo.duzenle(l1);
			}
		}
		
		return l1;
	}
	@PUT
	//@Secured
	@Path("jwt")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase jwtI(LoginBase l1) {
		
		Instant now = Instant.now(); 
		byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
		String jwt=Jwts.builder()
			.setSubject(l1.getUsername())
			.setAudience(l1.getUuid())
			.claim("uuid",l1.getUuid())
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.minus(200, ChronoUnit.MINUTES)))
			.signWith(Keys.hmacShaKeyFor(secret))
			.compact();	
		System.out.println(jwt);
		try {
			Jws<Claims> result= Jwts.parser()
					.requireAudience(l1.getUuid())//eşleşme kontrol
					.setAllowedClockSkewSeconds(122222)
					.setSigningKey(Keys.hmacShaKeyFor(secret))
					.parseClaimsJws(jwt);
			System.out.println("1d20 :"+ l1.getUuid());
			System.out.println(result);
		}		
		catch (Exception e) {
			System.out.println("unauthorized " + e );	
			System.out.println("token yanlis yada süresi dolmus");
		}
		return l1;
	}
	@PUT
	@Path("duzenle")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase getDuzen(LoginBase l1) {
				String data=l1.getPassword();
				String veri = BCrypt.hashpw(data, BCrypt.gensalt());
				l1.setPassword(veri);
				lo.getDuzenle(l1);
				return l1;			
	}
			

	@POST

	@Path("register")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase sifrele(LoginBase l1)
	{		
		String data=l1.getPassword();
		String veri = BCrypt.hashpw(data, BCrypt.gensalt());
		l1.setPassword(veri);
		lo.yeni(l1);	
	 //	l1.setPassword(veri);
		return l1;
	}
	@PUT
	@Path("login")

	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase duzenle(LoginBase l1)
	{
		System.out.println(l1);
		if(lo.getLogin(l1.getId()).getId()==0)
		{
			lo.yeni(l1);
		}else {
			{
				String data=l1.getPassword();
				String veri = BCrypt.hashpw(data, BCrypt.gensalt());
				l1.setPassword(veri);
				lo.duzenle(l1);
			}
		}
		
		return l1;
	}
	@PUT
	@Path("uyoklogin")

	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase uuidsizduzenle(LoginBase l1)//id si null sa yeni eleman eşleşme varsa değişim olur.
	{
		System.out.println(l1);
		if(lo.getLogin(l1.getId()).getId()==0)
		{
			lo.yeni(l1);
		}else {
			{
				String data=l1.getPassword();
				String veri = BCrypt.hashpw(data, BCrypt.gensalt());
				l1.setPassword(veri);
				lo.uuidsizduzenle(l1);
			}
		}
		
		return l1;
	}
	@DELETE
	@Path("login/{id}")
	public LoginBase sil(@PathParam("id") int id)
	{
		LoginBase l=lo.getLogin(id);
		if(l.getId()!=0)
		{
			lo.sil(id);
		}
		return l;
	}
}

/*
@POST
@Path("sifre")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public LoginBase sifrelerikontrol(LoginBase l1)
{
	//String plaintext="yok";
	if(lo.getLogin(l1.getId()).getId()==0)
	{
		lo.yeni(l1);
	}else {
		{
			/*String data=l1.getPassword();
			String veri = BCrypt.hashpw(data, BCrypt.gensalt());
		    l1.setPassword(veri);
			String hashed=l1.getPassword();
			boolean bool=BCrypt.checkpw(plaintext, hashed);
			if(bool==true) {
				System.out.println("sifre dogru");
				}else {
				System.out.println("sifre yanlıs");
				}*//*
			lo.duzenlemeler();
		}
	}
	
	return l1;
}*/


//import filter.Base64Util;
//import filter.Secured;
//import javax.websocket.server.PathParam;
//import org.eclipse.microprofile.jwt.JsonWebToken;
//import org.glassfish.jersey.process.internal.RequestScoped;

//import org.mindrot.jbcrypt.BCrypt;

//import javax.ws.rs.core.Response;
//import org.apache.tomcat.jni.User;
//import org.mindrot.jbcrypt.BCrypt;






/*
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

@Path("logins")
public class LoginResource {

	LoginDatabase lo= new LoginDatabase();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Login> getLogins(){
		System.out.println("gete ulaştı  ulaştı");
		return lo.getLogins();
	}
	@GET
	@Path("login/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Login getLogin(@PathParam("username")String username) {
		return lo.getLogin(username);
	}
	@POST
	@Path("login")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Login yeni(Login l1)
	{
		lo.yeni(l1);
		return l1;
	}
	@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
	@Path("login")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Login duzenle(Login l1)//id si null sa yeni eleman eşleşme varsa değişim olur.
	{
		System.out.println(l1);
		if(lo.getLogin(l1.getUsername()).getUsername()==null)
		{
			lo.yeni(l1);
		}else {
			{
				lo.duzenle(l1);//girdilerimizle a1 arayi oluşturuyor
			}
		}
		
		return l1;
	}
	
/*
	@PUT
	@Path("sifre")
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase sifrekontrol(LoginBase l1)
	{
		String plaintext="yok";
	//	String hashed=l1.getPassword();
	//	String hashed="$2a$10$spnrhIToIrDmCexn24L5LOjiq8YBj8IcEJMqo8KkCAAmj4P3xkq/6";
	//	String boString=l1.getPassword();
	//	System.out.println(boString);
		if(lo.getLogin(l1.getId()).getId()==0)
		{
			lo.yeni(l1);
		}else {
			{
			//	String data=l1.getPassword();
				//String veri = BCrypt.hashpw(data, BCrypt.gensalt());
			//	l1.setPassword(veri);
			//	String hashed=l1.getPassword();
			//	boolean bool=BCrypt.checkpw(plaintext, hashed);
			/*	if(bool==true) {
					System.out.println("sifre dogru");
					}else {
					System.out.println("sifre yanlıs");
					}
				lo.duzenle(l1);
			}
		}
		
		return l1;
	}*//*
	@DELETE
	@Path("login/{username}")//gette oldugu gibi şeçtiğimiz id yi siliyoruz
	public Login sil(@PathParam("username") String username)
	{
		Login l=lo.getLogin(username);
		if(l.getUsername()!=null)
		{
			lo.sil(username);
		}
		return l;
	}
}*/
