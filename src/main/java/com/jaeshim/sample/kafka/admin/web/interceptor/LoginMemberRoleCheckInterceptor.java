package com.jaeshim.sample.kafka.admin.web.interceptor;

import com.jaeshim.sample.kafka.admin.domain.member.SessionMember;
import com.jaeshim.sample.kafka.admin.role.MemberRole;
import com.jaeshim.sample.kafka.admin.web.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginMemberRoleCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("사용자 권한 체크 인터셉터");
    String requestURI = request.getRequestURI();

    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute(SessionConst.SESSION_COOKIE_NAME)==null) {
      response.sendRedirect("/");
      return false;
    }

    SessionMember member = (SessionMember) session.getAttribute(SessionConst.SESSION_COOKIE_NAME);
    if (!member.getRole().equals(MemberRole.ADMIN)) {
      response.sendRedirect("/");
      return false;
    }

    return true;
  }
}
