package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mattia Lenza
 */
@WebFilter({"/richiesta_tirocinio", "/Profilo"})
public class AuthGuard implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String ref = null;
        HttpServletRequest http_request = (HttpServletRequest)request;
        System.err.println(http_request.getRequestURI());

        HttpServletResponse http_response = (HttpServletResponse)response;

        // pass the request along the filter chain

        HttpSession sessione = http_request.getSession(); //sessione
        if (sessione.getAttribute("username") != null){
            //Utente loggato
            chain.doFilter(request, response); //vai tranquillo
        } else {
            //Utente non loggato
            http_response.sendRedirect("Login");
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    
}
