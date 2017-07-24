package ar.org.blb.security.administration.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/foo/")
public class FooController {

    @GetMapping
    public ResponseEntity<String> readFoo() {
        return new ResponseEntity<>("Read Foo " + UUID.randomUUID().toString(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> writeFoo() {
        return new ResponseEntity<>("Write Foo " + UUID.randomUUID().toString(), HttpStatus.OK);
    }
}
