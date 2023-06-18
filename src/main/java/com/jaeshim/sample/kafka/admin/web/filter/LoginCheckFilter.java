package com.jaeshim.sample.kafka.admin.web.filter;

import com.jaeshim.sample.kafka.admin.web.SessionConst;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class LoginCheckFilter implements Filter {

  private static final String[] whitelist = {"/","/join","/login","/logout","/css/*"};

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String requestURI = httpRequest.getRequestURI();

    try{
      if (isLoginCheckPath(requestURI)) {
        log.info("로그인 인증 시작");
        HttpSession session = httpRequest.getSession(false);

        if (session == null || session.getAttribute(SessionConst.SESSION_COOKIE_NAME) == null) {
          log.info("로그인되지 않은 사용자");
          httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
          return;
        }
      }
      chain.doFilter(request, response);
    }catch (Exception e){
      throw e;
    }finally {

    }
  }

  private boolean isLoginCheckPath(String requestURI){
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }
}
