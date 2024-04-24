package com.ji.spring;

import org.springframework.core.convert.converter.Converter;

public class ComplaintHandlerConverter implements Converter<String, ComplaintHandler> {

    @Override
    public ComplaintHandler convert(String source) {
        ComplaintHandler complaintHandler = new ComplaintHandler();
        complaintHandler.setName(source);
        return complaintHandler;
    }
}
