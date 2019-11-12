package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陶波利
 */
@Controller
@Api(tags = "前台页面跳转")
public class ForePageController {
    @GetMapping(value = "/")
    @ApiOperation(value = "由 / 跳转到 /home")
    public String index() {
        return "redirect:home";
    }

    @GetMapping(value = "/home")
    @ApiOperation(value = "由 /home 跳转到 home.html")
    public String home() {
        return "fore/home";
    }
}
