package esgi.ascl.security.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private final List<String> allowedOrigins = List.of("http://localhost:4200");
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String origin = req.getHeader("Origin");
        if(origin == null) origin = "*";
        else if (!allowedOrigins.contains(origin)) origin = "*";
        res.setHeader("Access-Control-Allow-Origin", origin);

        res.setHeader( "Access-Control-Allow-Credentials", "true" );
        //res.setHeader("Vary", "Origin");
        res.setHeader( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS" );
        res.setHeader( "Access-Control-Allow-Headers", "*" );

        /*
        // Just REPLY OK if request method is OPTIONS for CORS (pre-flight)
        if ( req.getMethod().equals("OPTIONS") ) {
            res.setHeader( "Access-Control-Max-Age", "86400" );
            res.setStatus( HttpServletResponse.SC_OK );
            return;
        }

         */

        chain.doFilter( request, response );
    }
}
