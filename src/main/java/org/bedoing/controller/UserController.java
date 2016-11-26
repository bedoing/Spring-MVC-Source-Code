package org.bedoing.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Ken
 */
@Controller
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    // 处理HEAD类型的"/"请求
    @RequestMapping(value = "/", method = HEAD)
    public String head() {
        return "user.jsp";
    }

    @RequestMapping(value = {"/user", "/"}, method = GET)
    public String user(Model model) {
        logger.info("enter user: " + model);
        model.addAttribute("msg", "for user test");
        return "user.jsp";
    }
}
