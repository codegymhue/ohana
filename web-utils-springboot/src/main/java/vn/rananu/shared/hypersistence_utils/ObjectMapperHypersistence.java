package vn.rananu.shared.hypersistence_utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import vn.rananu.shared.parsers.JacksonParser;

//hypersistence-utils.properties file
//hypersistence.utils.jackson.object.mapper=vn.sapo.shared.hypersistence_utils.ObjectMapperHypersistence
public class ObjectMapperHypersistence implements  io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier{
    @Override
    public ObjectMapper get() {
        return JacksonParser.INSTANCE.getMapper();
    }
}
