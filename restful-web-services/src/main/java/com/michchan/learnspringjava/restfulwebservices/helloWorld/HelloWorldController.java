package com.michchan.learnspringjava.restfulwebservices.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@RestController
public class HelloWorldController {
    @Autowired
    private MessageSource messageSource;

    // @RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // @RequestMapping(method = RequestMethod.GET, path = "/hello-world-bean")
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean () {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBeanName (@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = {"/hello-world-i18n", "/hello-world-i18n/{name}"})
    public String helloWorldInternationalized (
        @RequestHeader(name = "Accept-Language", required = false) Locale locale,
        @PathVariable(required = false) String name
    ) {
        String displayName = "John Doe";
        if (name != null) displayName = name;
        // For Chinese/Japanese etc. characters, the message should be put in unicode like "\u4f60\u597d"
        return messageSource
                .getMessage("good.morning.message", new String[]{displayName, "123"}, "Default Message", LocaleContextHolder.getLocale());
    }
}
