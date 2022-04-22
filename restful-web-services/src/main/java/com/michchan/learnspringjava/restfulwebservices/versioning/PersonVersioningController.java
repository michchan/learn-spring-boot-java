package com.michchan.learnspringjava.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {
    // Factors:
    // - URI Pollution  -> URI versioning
    // - Misuse of HTTP headers  -> Header versioning
    // - Caching  -> Hard for Header / MIME type versioning
    // - Can we execute on the browser  -> Cannot directly: Header / MIME type versioning
    // - How easy is it to generate API Documentation  -> easy for URI/Request parameter versioning

    /* ------------- URI Versioning ------------- */
    @GetMapping("/v1/person")
    public PersonV1 personV1() {
        return new PersonV1("John Doe");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("John", "Doe"));
    }

    /* ------------- Request Parameter Versioning (query) ------------- */
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1("John Doe");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("John", "Doe"));
    }

    /* ------------- Request Parameter Versioning (header) ------------- */
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerV1() {
        return new PersonV1("John Doe");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name("John", "Doe"));
    }

    /* ------------- MIME type / Content Negotiation / Accept Versioning ------------- */
    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
    public PersonV1 producesV1() {
        return new PersonV1("John Doe");
    }

    // Another naming convention
    @GetMapping(value = "/person/produces", produces = "application/vnd.api+json; version=2")
    public PersonV2 producesV2() {
        return new PersonV2(new Name("John", "Doe"));
    }
}
