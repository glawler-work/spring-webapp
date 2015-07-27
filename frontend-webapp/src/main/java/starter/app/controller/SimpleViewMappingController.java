package starter.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SimpleViewMappingController {

    @RequestMapping(value = "/authenticate")
    public ModelAndView getLoginPage() {
        return new ModelAndView("authenticate");
    }

    @RequestMapping(value = "/loggedOut")
    public String getLogoutPage(HttpServletRequest request) {
        request.getSession().invalidate();
        return "loggedOut";
    }
}
