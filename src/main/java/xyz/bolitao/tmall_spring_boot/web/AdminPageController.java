package xyz.bolitao.tmall_spring_boot.web;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 陶波利
 */
@Controller
@Api(tags = "admin 界面跳转")
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

    @GetMapping(value = "/admin_order_list")
    public String listOrder() {
        return "admin/listOrder";

    }

    @GetMapping(value = "/admin_product_list")
    public String listProduct() {
        return "admin/listProduct";

    }

    @GetMapping(value = "/admin_product_edit")
    public String editProduct() {
        return "admin/editProduct";

    }

    @GetMapping(value = "/admin_productImage_list")
    public String listProductImage() {
        return "admin/listProductImage";

    }

    @GetMapping(value = "/admin_property_list")
    public String listProperty() {
        return "admin/listProperty";

    }

    @GetMapping(value = "/admin_property_edit")
    public String editProperty() {
        return "admin/editProperty";

    }

    @GetMapping(value = "/admin_propertyValue_edit")
    public String editPropertyValue() {
        return "admin/editPropertyValue";

    }

    @GetMapping(value = "/admin_user_list")
    public String listUser() {
        return "admin/listUser";
    }
}