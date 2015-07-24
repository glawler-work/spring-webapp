package starter.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import starter.app.listener.HelloListener;

@RestController()
public class HelloRestController {

    @Autowired
    protected HelloListener helloListener;

    @RequestMapping(value = "/api/hello", method = RequestMethod.GET, produces = "application/json")
    public String numberOfHellos() {
        return String.valueOf(helloListener.getHellos().size());
    }
}
