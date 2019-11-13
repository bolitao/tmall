package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import xyz.bolitao.tmall_spring_boot.comparator.*;
import xyz.bolitao.tmall_spring_boot.pojo.*;
import xyz.bolitao.tmall_spring_boot.service.*;
import xyz.bolitao.tmall_spring_boot.util.Result;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

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

    @PostMapping("/forelogin")
    @ApiOperation(value = "用户登录")
    public Object login(@RequestBody User userParam, HttpSession session) { //TODO: 使用 token
        String name = userParam.getName();
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, userParam.getPassword());
        if (null == user) {
            String message = "账号密码错误";
            return Result.fail(message);
        } else {
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @GetMapping("/foreproduct/{pid}")
    @ApiOperation(value = "某产品信息")
    public Object product(@PathVariable("pid") int pid) { // TODO: L
        Product product = productService.get(pid);
        List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        List<Review> reviews = reviewService.list(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);
        Map<String, Object> map = new HashMap<>();
        map.put("product", product);
        map.put("pvs", propertyValues);
        map.put("reviews", reviews);
        return Result.success(map);
    }

    @GetMapping("forecheckLogin")
    @ApiOperation(value = "检查是否登录")
    public Object checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return Result.success();
        }
        return Result.fail("未登录");
    }

    @GetMapping("forecategory/{cid}")
    @ApiOperation(value = "返回按 sort 值排序的分类")
    public Object category(@PathVariable int cid, String sort) {
        Category category = categoryService.get(cid);
        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());
        categoryService.removeCategoryFromProduct(category);
        if (null != sort) {
            switch (sort) {
                case "review":
                    category.getProducts().sort(new ProductReviewComparator());
                    break;
                case "date":
                    category.getProducts().sort(new ProductDateComparator());
                    break;
                case "saleCount":
                    category.getProducts().sort(new ProductSaleCountComparator());
                    break;
                case "price":
                    category.getProducts().sort(new ProductPriceComparator());
                    break;
                case "all":
                    category.getProducts().sort(new ProductAllComparator());
                    break;
                default:
            }
        }
        return category;
    }

    @PostMapping("foresearch")
    public Object search(String keyword) {
        if (null == keyword) {
            keyword = "";
        }
        List<Product> ps = productService.search(keyword, 0, 20);
        productImageService.setFirstProductImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }
}
