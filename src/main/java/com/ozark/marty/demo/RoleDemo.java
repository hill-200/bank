package com.ozark.marty.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class RoleDemo {
    @GetMapping
    public String getMapping(){
        return "Welcome to GET mapping";
    }

    @PutMapping
    public String putMapping(){
        return "Welcome to PUT mapping";
    }

    @PostMapping
    public String postMapping(){
        return "Welcome to POST mapping";
    }

    @DeleteMapping
    public String deleteMapping(){
        return "Welcome to DELETE mapping";
    }
}
