// package com.ams.app.security;
// import com.ams.app.exception.CustomErrorResponse;
// import com.ams.app.model.AuthenticationRequest;
// import com.ams.app.service.MyUserDetailsService;
// import com.ams.app.service.ObjectMapperService;
// import com.ams.app.util.JwtUtil;
// import io.jsonwebtoken.ExpiredJwtException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.Cookie;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.BufferedReader;
// import java.io.IOException;

// @Component
// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     @Autowired
//     private MyUserDetailsService userDetailsService;

//     @Autowired
//     private JwtUtil jwtUtil;
    
//     private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    

//     // @Override
//     // protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

//     //     if (request.getRequestURI().matches("/**")){
//     //         return false;
//     //     }else{
//     //         return true;
//     //     }
// 	// }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//             throws ServletException, IOException {
//             try{    

//                 String jwtCookie = getJWTCookieValue(request, "LoginToken");
//                 System.out.println("attempt for authentication");
//                 System.out.println("Cookie value :"+jwtCookie);
                
//                 if (jwtCookie != null) {
//                     System.out.println("JWT Cookie found. processing it for authentication");
//                     String username = jwtUtil.extractUsername(jwtCookie);
//                     if(username != null) {
//                         System.out.println("Username found");
//                         UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                         if (jwtUtil.validateToken(jwtCookie, userDetails)) {
//                             //auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                             UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                         userDetails, null, userDetails.getAuthorities());
//                 usernamePasswordAuthenticationToken
//                         .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                             System.out.println("Authenticated");
//                         }
//                     }
//                 } else if(SecurityContextHolder.getContext().getAuthentication() == null){
        
//                     AuthenticationRequest req = getPostData(request);
//                     if(req != null){
//                         if(req.getUsername() != null && req.getPassword()!=null){
//                             System.out.println("requested to login:"+req.getUsername());
//                             UserDetails userDetails = this.userDetailsService.loadUserByUsername(req.getUsername());
//                             //req.setPassword(passwordEncoder.encode(req.getPassword()));
//                             // if(userDetails.getPassword() == req.getPassword()){
//                             //     System.out.println("matched");
//                             // }

//                             Authentication auth = new AuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

//                             //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//                         //userDetails, req);
//                 //         if(usernamePasswordAuthenticationToken.isAuthenticated()){
//                 //             System.out.println("Hello");
//                 //         }else{
//                 //             System.out.println("Failed");
//                 //         }
//                 // usernamePasswordAuthenticationToken
//                 //         .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 if(auth != null){    
//                 SecurityContext context = SecurityContextHolder.createEmptyContext();
//                     context.setAuthentication(auth);
//                     SecurityContextHolder.setContext(context);
                    
//                             System.out.println("Login successful");
//                             final String jwt = jwtUtil.generateToken(userDetails);
//                             Cookie loginCookie = new Cookie("LoginToken", jwt);
//                             loginCookie.setMaxAge(86400 * 5);  // Set cookie expiration time
//                             loginCookie.setPath("/");
//                             response.addCookie(loginCookie);
//                             System.out.println("Token cookie created");
//                     }
//                         }
//                     }
//                 }

                
//         }catch (ExpiredJwtException e){
//             System.out.println("Authentication token is expired !");
//             Cookie loginCookie = new Cookie("LoginToken","");
//             loginCookie.setMaxAge(0);
//             response.addCookie(loginCookie);
//             sendErrorResponse(response,"Authentication token is expired !");
//             return;
//         }catch (Exception e) {
//             sendErrorResponse(response, "Authentication error: " + e.getMessage());
//             return;
//         }
//         chain.doFilter(request, response);
//     }

//     private AuthenticationRequest getPostData(HttpServletRequest request) throws IOException, BadCredentialsException {
//     BufferedReader reader = request.getReader();
//     StringBuilder sb = new StringBuilder();
//     String line = reader.readLine();
//     while (line != null) {
//       sb.append(line + "\n");
//       line = reader.readLine();
//     }
//     reader.close();
//     return new ObjectMapperService().parseJson(sb.toString(), AuthenticationRequest.class);
//   }

//     private String getJWTCookieValue(HttpServletRequest request, String name) {
//     String cookieValue = null;
//     Cookie[] cookies = request.getCookies();
//     if (cookies != null) {
//       for (Cookie cookie : cookies) {
//         if (cookie.getName().equals(name)) {
//           cookieValue = cookie.getValue();
//         }
//       }
//     }
//     return cookieValue;
//   }

//   private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
//     CustomErrorResponse res = new CustomErrorResponse(401, errorMessage, "/");
//     String resp = new ObjectMapperService().convertToJson(res);
//     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//     response.setContentType("application/json");
//     response.getWriter().write(resp);
// }

// }
