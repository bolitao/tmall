package xyz.bolitao.tmall_spring_boot.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陶波利
 */
@Controller
public class AdminPageController {
    /**
     * @return 跳转
     */
    @GetMapping(value = "/admin")
    public String admin() {
        return "redirect:admin_category_list";
    }

    /**
     * @return 服务端跳转到 listCategory.html
     */
    @GetMapping(value = "/admin_category_list")
    public String listCategory() {
        return "admin/listCategory";
    }

    /**
     * @return 服务端跳转到 editCategory.html
     */
    @GetMapping(value = "/admin_category_edit")
    public String editCategory() {
        return "admin/editCategory";
    }
}