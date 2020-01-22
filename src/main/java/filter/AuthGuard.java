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
@WebFilter({"/richiedi_tirocinio", "/profilo"})
public class AuthGuard implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest http_request = (HttpServletRequest)request;
        HttpServletResponse http_response = (HttpServletResponse)response;
        HttpSession sessione = http_request.getSession();
                
        if (sessione.getAttribute("username") != null){
            //Utente loggato
            chain.doFilter(request, response); //vai tranquillo
        } else {
            //Utente non loggato
            http_response.sendRedirect("login");
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    
}
