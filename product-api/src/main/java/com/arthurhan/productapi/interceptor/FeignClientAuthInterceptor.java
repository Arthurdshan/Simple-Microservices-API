package com.arthurhan.productapi.interceptor;

import com.arthurhan.productapi.exception.ValidationException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.fileupload.RequestContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class FeignClientAuthInterceptor implements RequestInterceptor
{
    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        HttpServletRequest currentRequest = getCurrentRequest();

        requestTemplate.header("Authorization", currentRequest.getHeader("Authorization"));
    }

    private HttpServletRequest getCurrentRequest()
    {
        try
        {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();

        } catch (Exception e)
        {
            throw new ValidationException("The current request could not be processed");
        }
    }
}
