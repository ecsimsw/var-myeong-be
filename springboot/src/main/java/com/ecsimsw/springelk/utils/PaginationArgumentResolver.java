package com.ecsimsw.springelk.utils;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PaginationArgumentResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
        if (methodParameter.hasMethodAnnotation(LimitedSizePagination.class)) {
            final int maxSize = methodParameter.getMethodAnnotation(LimitedSizePagination.class).maxSize();
            if (pageable.getPageSize() > maxSize) {
                throw new IllegalArgumentException("page size can't be bigger than " + maxSize);
            }
        }
        return pageable;
    }
}
