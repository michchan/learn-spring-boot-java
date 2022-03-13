package com.michchan.learnspringjava.restfulwebservices.helloWorld;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {
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
}
