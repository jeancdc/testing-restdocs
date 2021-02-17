package com.example.testingrestdocs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/greeting")
    public Map<String, Object> greeting(@RequestParam() String name) {
        return Collections.singletonMap("message", "Hello, " + name);
    }
}
