package com.sn.ex.zuulproxy;

import com.sn.ex.zuulproxy.model.AuthenticationRequest;
import com.sn.ex.zuulproxy.model.AuthenticationResponse;
import com.sn.ex.zuulproxy.service.AppUserDetailsService;
import com.sn.ex.zuulproxy.service.JwtUtilService;
import com.sn.ex.zuulproxy.zuulfilter.ErrorFilter;
import com.sn.ex.zuulproxy.zuulfilter.PostFilter;
import com.sn.ex.zuulproxy.zuulfilter.PreFilter;
import com.sn.ex.zuulproxy.zuulfilter.RouteFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableZuulProxy
//@EnableDiscoveryClient
@SpringBootApplication
public class ZuulProxyApplication {

	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
	@Bean
	public ErrorFilter errorFilter() {
		return new ErrorFilter();
	}
	@Bean
	public RouteFilter routeFilter() {
		return new RouteFilter();
	}


	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}


}
