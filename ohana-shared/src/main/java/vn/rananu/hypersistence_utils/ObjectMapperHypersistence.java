package vn.rananu.hypersistence_utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.rananu.parsers.JacksonParser;

public class ObjectMapperHypersistence implements  io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier{
    @Override
    public ObjectMapper get() {
        return JacksonParser.INSTANCE.getMapper();
    }
}
