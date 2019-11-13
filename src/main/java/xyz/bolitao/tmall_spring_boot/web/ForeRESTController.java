package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import xyz.bolitao.tmall_spring_boot.pojo.Category;
import xyz.bolitao.tmall_spring_boot.pojo.User;
import xyz.bolitao.tmall_spring_boot.service.CategoryService;
import xyz.bolitao.tmall_spring_boot.service.ProductService;
import xyz.bolitao.tmall_spring_boot.service.UserService;
import xyz.bolitao.tmall_spring_boot.util.Result;

import java.util.List;

/**
 * @author 陶波利
 */
@RestController
@Api(tags = "用户前端相关操作")
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/forehome")
    @ApiOperation(value = "查询所有分类")
    public Object home() {
        List<Category> categoryList = categoryService.list();
        productService.fill(categoryList);
        productService.fillByRow(categoryList);
        categoryService.removeCategoryFromProduct(categoryList);
        return categoryList;
    }

    @PostMapping("/foreregister")
    @ApiOperation(value = "用户注册")
    public Object register(@RequestBody User user) {
        String name = user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);
        if (exist) {
            String message = "用户名已经被使用";
            return Result.fail(message);
        }
        user.setPassword(password);
        userService.add(user);
        return Result.success();
    }
}
