package com.jaeshim.sample.kafka.admin.web.argumentresolver;

import com.jaeshim.sample.kafka.admin.domain.member.SessionMember;
import com.jaeshim.sample.kafka.admin.web.SessionConst;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {

    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
    boolean hasSessionMemberType = SessionMember.class.isAssignableFrom(parameter.getParameterType());

    return hasLoginAnnotation && hasSessionMemberType;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

    HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
    HttpSession session = request.getSession(false);
    if (session == null) {
      return null;
    }

    return session.getAttribute(SessionConst.SESSION_COOKIE_NAME);
  }
}
