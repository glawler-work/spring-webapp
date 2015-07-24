package starter.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import starter.app.listener.HelloListener;

@Controller()
public class HelloController {

    @Autowired
    protected HelloListener helloListener;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello() {

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.getModelMap().addAttribute("result", helloListener.getHellos());

        return modelAndView;
    }
}
