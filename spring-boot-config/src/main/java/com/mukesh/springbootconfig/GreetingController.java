package com.mukesh.springbootconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GreetingController {

    @Value("${my.greeting: default value}")
    private String greetingMessage;

    @Value("${app.description}")
    private String description;

    @Value("Some static message")
    private String staticMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

    @Value("#{${dbValues}}")
    private Map<String,String> dbValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment env;

    @GetMapping("/greeting")
    public String greeting() {
        return greetingMessage +"\n"+description +" "+staticMessage +" "+listValues
                +"  "+dbValues
                +"  "+dbSettings.getConnection()
                +"  "+dbSettings.getHost()
                +"  "+dbSettings.getPort();
    }

    @GetMapping("/envdetails")
    public String envDetails(){
        return env.toString();
    }
}
