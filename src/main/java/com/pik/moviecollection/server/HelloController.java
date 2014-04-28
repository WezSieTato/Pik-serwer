package com.pik.moviecollection.server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/service/greeting")
@ResponseStatus(HttpStatus.OK)
public class HelloController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String getGreeting(@PathVariable String name) {
        String result="Hello "+name;
        return result;
        //return new ResponseEntity<String>("{value: " + result + "}", HttpStatus.OK);
    }
}
