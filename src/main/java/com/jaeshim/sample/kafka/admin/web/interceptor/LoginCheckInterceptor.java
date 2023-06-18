package com.jaeshim.sample.kafka.admin.web.interceptor;

import com.jaeshim.sample.kafka.admin.web.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String requestURI = request.getRequestURI();
    log.info("로그인 체크 인터셉터{}", requestURI);

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute(SessionConst.SESSION_COOKIE_NAME) == null) {
      log.info("로그인되지 않은 사용자");
      response.sendRedirect("/login?redirectURL=" + requestURI);
      return false;
    }
    return true;

  }
}
