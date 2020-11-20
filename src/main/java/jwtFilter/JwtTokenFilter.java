package jwtFilter;
/*
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired
	private TokenManager tokenManager;
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader =request.getHeader	("Authorization");
		String username=null;
		String token=null;
		if(authHeader !=null && authHeader.contains("Bearer")) {
			token= authHeader.substring(7);
			
			try {
				username=tokenManager.getUsernameToken(token);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (username != null && token != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			if (tokenManager.tokenValidate(token)) {
				UsernamePasswordAuthenticationToken upassToken=new UsernamePasswordAuthenticationToken(username, null,new ArrayList());
				upassToken.setDetails(new webAuth);
			}
		}
		filterChain.doFilter(request, response);
	}

}*/
