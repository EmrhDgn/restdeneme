package filter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
//import java.util.Random;

import org.emrah.demorest.LoginBase;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

//import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

 
public class TokenManager {
				//Instant now = Instant.now(); 
				//open ssl rand base64 32 ile le yeni bir yer aldı ben internetten aldm ondan secret güvenli degil.
				//byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
	 			//b8SwFJZVgo+S5Cuhf5LWUeXpHxDm5mp30GCuQHX2TpY=
		public String Token(LoginBase l1)	{		

			Instant now = Instant.now(); 
			//open ssl rand base64 32 ile le yeni bir yer aldı ben internetten aldm ondan secret güvenli degil.
			byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
				String jwt=Jwts.builder()
					.setSubject(l1.getUsername())
					.setAudience(l1.getUuid())
					.claim("uuid",l1.getUuid()) //new Random().nextInt(20) +1 )
					.setIssuedAt(Date.from(now))//tokenin başladıgı tarih
				//	.setExpiration(Date.from(now.plus(2,ChronoUnit.MINUTES)))// aldıktan 2dk sonrasına kadar.
					.setExpiration(Date.from(now.minus(2, ChronoUnit.MINUTES)))//yapıldıktan ne kadar süre sonra kapanacak
					.signWith(Keys.hmacShaKeyFor(secret))//imza 
					.compact();	//sıkırma (birleştirme)
			return jwt;
		}		
		public String tokenKontrol(String jwt,LoginBase l1) {
			byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
			Jws<Claims> result= Jwts.parser()
					.requireAudience(l1.getUuid())//eşleşme kontrol
					.setAllowedClockSkewSeconds(122)
					.setSigningKey(Keys.hmacShaKeyFor(secret))
					.parseClaimsJws(jwt);
			String k1=result.toString();
			//System.out.println(result);
			return k1;
			
		}

	/*	public boolean tokenValidate(String token ) {
			if(getUsernameToken(token) !=null && isExpired(token)) {
				
				return true;
			}
			return false;
		}
		public String getUsernameToken(String token) {
			Claims claims = getClaims(token);
			return claims.getSubject();
		}
		public boolean isExpired(String token) {
			Claims claims = getClaims(token);
			return claims.getExpiration().before(new Date(System.currentTimeMillis()));
		}
		private Claims getClaims(String token) {
			byte[] secret=Base64.getDecoder().decode("o40dCNjd8mmDN2+/nfHdIB2ZWta80foXqDx2roul4nw=");
			Claims claims= Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secret)).parseClaimsJws(token).getBody();
			return claims;
		}*/
}

/*	Jws<Claims> result= Jwts.parser()
.requireAudience("video demo")//eşleşme kontrol
.setAllowedClockSkewSeconds(182)
.setSigningKey(Keys.hmacShaKeyFor(secret))
.parseClaimsJws(jwt);*/
//	System.out.println("1d20 :"+ result.getBody().get("1d20",Integer.class));
//	System.out.println(result);
//.claim(name="1d20", value=new Random().nextInt(bound=20) +1 ) intelij kullanımı
