package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.bolitao.tmall_spring_boot.pojo.User;
import xyz.bolitao.tmall_spring_boot.service.UserService;
import xyz.bolitao.tmall_spring_boot.util.Page4Navigator;

/**
 * @author 陶波利
 */
@RestController
@Api(tags = "admin 用户相关")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ApiOperation("分页查询")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                     @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<User> page = userService.list(start, size, 5);
        return page;
    }
}