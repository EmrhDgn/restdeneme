package org.emrah.demorest;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Random;

//import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.security.crypto.bcrypt.BCrypt;

import filter.Secured;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;





//import org.eclipse.microprofile.jwt.JsonWebToken;
//import org.glassfish.jersey.process.internal.RequestScoped;

//import org.mindrot.jbcrypt.BCrypt;

//import javax.ws.rs.core.Response;
//import org.apache.tomcat.jni.User;
//import org.mindrot.jbcrypt.BCrypt;


@Path("logins")
public class LoginResource {//extends AuthenticationFilter{
	LoginDatabase lo= new LoginDatabase();
	public LoginBase jwt1(LoginBase l1) {
		Instant now = Instant.now(); 
		byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
		String jwt=Jwts.builder()
			.setSubject(l1.getUsername())
			.setAudience(l1.getUuid())
			.claim("1d20", new Random().nextInt(20) +1 )
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.minus(2, ChronoUnit.MINUTES)))
			.signWith(Keys.hmacShaKeyFor(secret))
			.compact();	
		System.out.println(jwt);
		return l1;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LoginBase> getLogins(){
		System.out.println("gete ulaştı  ulaştı");
		return lo.getLogins();
	}
	
	@GET
	@Path("login/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LoginBase getLogin(@PathParam("id")int id) {
		//String data="emrah";
	//	String veri = BCrypt.hashpw(data, BCrypt.gensalt());
		//System.out.println(veri);
		
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
			.claim("1d20", new Random().nextInt(20) +1 )
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.minus(2, ChronoUnit.MINUTES)))
			.signWith(Keys.hmacShaKeyFor(secret))
			.compact();	
		System.out.println(jwt);
		//UUID uuid = UUID.randomUUID();
		//String u=uuid.toString();
		//l1.setUuid(u);
		try {
			Jws<Claims> result= Jwts.parser()
					.requireAudience(l1.getUuid())//eşleşme kontrol
					.setAllowedClockSkewSeconds(122)
					.setSigningKey(Keys.hmacShaKeyFor(secret))
					.parseClaimsJws(jwt);
			System.out.println("1d20 :"+ result.getBody().get("1d20",Integer.class));
			System.out.println(result);
			System.out.println();

		}		
		catch (Exception e) {
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
	//	String hashed=l1.getPassword();
	//	String hashed="$2a$10$spnrhIToIrDmCexn24L5LOjiq8YBj8IcEJMqo8KkCAAmj4P3xkq/6";
	//	String boString=l1.getPassword();
	//	System.out.println(boString);
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
					}else {
					System.out.println("sifre yanlıs");
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
		/*	Claims claims;
	    
	        claims = Jwts.parser()
	        		.requireAudience(l1.getUuid())
	        		.setSigningKey(Keys.hmacShaKeyFor(secret))
	                .parseClaimsJws(jwt)
	               .getBody();*/
			System.out.println("1d20 :"+ l1.getUuid());
			System.out.println(result);
			//System.out.println();

		}		
		catch (Exception e) {
			System.out.println("unauthorized " + e );	
			System.out.println("token yanlis yada süresi dolmus");
		}
		return l1;
	}
	@POST
	@Secured
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
	@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
	@Path("login")
	@Secured
	@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public LoginBase duzenle(LoginBase l1)//id si null sa yeni eleman eşleşme varsa değişim olur.
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
	@PUT//bilgi eklemek için kullanıyoruz yeni bir body oluştuyuruz.
	@Path("uyoklogin")
	@Secured
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
	@Path("login/{id}")//gette oldugu gibi şeçtiğimiz id yi siliyoruz
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
