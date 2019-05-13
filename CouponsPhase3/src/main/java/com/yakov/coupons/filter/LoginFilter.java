package com.yakov.coupons.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.yakov.coupons.cookies.CookieUtils;

@Component

public class LoginFilter implements Filter {

	
	
	//array of white urls
	//filter will not be used on those urls.
	private final String[] whiteList = {
			".+nameById.*",
			".+isByName/$",
			".+Login.+",
			"^GET.+Companies/$",
			"^GET.+Customers/$",
			"^POST.+/rest/Customers/$",
			"^POST.+/rest/Companies/$",
			"^GET.+Coupons.*",
			"^GET.+/byNameAndPassword$"
	};

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// class HttpServlet does not have the method getCookies,
		// YET.. the actual object IS (!) HttpServletRequest (HttpServletRequest extends ServletRequest)
		// and HttpServletRequest DOES have getCookies()
		// So, we cast request into a pointer of type HttpServletRequest
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response; 
		HttpSession session = req.getSession(false);
		String fullPath = req.getMethod() + req.getRequestURL();
		
		Cookie[] cookies = req.getCookies();
		String name = null;
		String password = null;
		String type = null;

		System.out.println("SN: "+ session + " FP: " + fullPath + " CK: " + cookies);		
		
		//If method is OPTIONS we skip it - pass it to CORSFilter.
        if (req.getMethod().equals("OPTIONS")) {
			chain.doFilter(request, response);
			return;
        }

		
		//if requested url is our white-list we do not apply any filter.
		for (String whiteUrl : whiteList) {
			if(fullPath.matches(whiteUrl)) {
				chain.doFilter(request, response);
				System.out.println(fullPath + " whitelist");
				return;
			}
		}
		
		
		/*
		 * 
		 * This some kind of Hacker protection.
		 * It confronts those who tries to manipulate urls and cookies.
		 * Basically we check cookies with server DB each time we are requested a new url.
		 * 
		 */
		
		
		if (session!=null && cookies != null) {
			//getting data to check from cookies.
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(CookieUtils.name)) {
					name = cookie.getValue();
				}
				if (cookie.getName().equals(CookieUtils.password)) {
					password = cookie.getValue();
				}
				if (cookie.getName().equals(CookieUtils.type)) {
					type = cookie.getValue();
				}
			}	
			//if we got required cookies, we check them with server, depending on user type.
			if(name != null && password != null && type != null) {
				if(type.equals(CookieUtils.customerType) || type.equals(CookieUtils.companyType)) {
					chain.doFilter(request, response);
					return;
				}
			}		
		}
		res.setStatus(401);
	}
	
	// Following methods are unimportant for now
	public void destroy() {
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}