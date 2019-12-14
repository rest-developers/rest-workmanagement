package com.work.management.logging;

import com.work.management.service.log.LoggingService;
import java.lang.reflect.Type;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
final class LogRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {

  private final LoggingService loggingService;
  private final HttpServletRequest httpServletRequest;

  @Autowired
  LogRequestBodyAdviceAdapter(LoggingService loggingService,
      HttpServletRequest httpServletRequest) {
    this.loggingService = loggingService;
    this.httpServletRequest = httpServletRequest;
  }

  @Override
  public boolean supports(MethodParameter methodParameter, Type targetType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
      Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
    loggingService.logRequest(httpServletRequest, body);
    return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
  }
}
