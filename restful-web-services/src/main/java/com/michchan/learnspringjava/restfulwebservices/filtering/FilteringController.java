package com.michchan.learnspringjava.restfulwebservices.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {
    private SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
    private FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

    private MappingJacksonValue withFilters(Object object) {
        MappingJacksonValue mapping = new MappingJacksonValue(object);
        mapping.setFilters(filters);
        return mapping;
    }

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return withFilters(someBean);
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveSomeBeansList() {
        List list = Arrays.asList(
            new SomeBean("value1", "value2", "value3"),
            new SomeBean("value11", "value22", "value33")
        );
        return withFilters(list);
    }
}
