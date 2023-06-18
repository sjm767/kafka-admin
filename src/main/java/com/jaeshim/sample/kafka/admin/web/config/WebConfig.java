package com.jaeshim.sample.kafka.admin.web.config;

import com.jaeshim.sample.kafka.admin.web.argumentresolver.LoginMemberArgumentResolver;
import com.jaeshim.sample.kafka.admin.web.filter.LoginCheckFilter;
import com.jaeshim.sample.kafka.admin.web.interceptor.LoginCheckInterceptor;
import com.jaeshim.sample.kafka.admin.web.interceptor.LoginMemberRoleCheckInterceptor;
import java.util.List;
import javax.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoginCheckInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/", "/join","/login","/logout", "/css/**","/*.ico","/error/**");

    registry.addInterceptor(new LoginMemberRoleCheckInterceptor())
        .order(2)
        .addPathPatterns("/kafka/brokers","/kafka/broker/**")
        .excludePathPatterns("/css/**","/*.ico","/error/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new LoginMemberArgumentResolver());
  }

  //  @Bean
  public FilterRegistrationBean loginCheckFilter(){
    FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
    filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
    filterFilterRegistrationBean.setOrder(1);
    filterFilterRegistrationBean.addUrlPatterns("/*");

    return filterFilterRegistrationBean;
  }

}
