package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import xyz.bolitao.tmall_spring_boot.comparator.*;
import xyz.bolitao.tmall_spring_boot.pojo.*;
import xyz.bolitao.tmall_spring_boot.service.*;
import xyz.bolitao.tmall_spring_boot.util.Result;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    OrderService orderService;

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
    @ApiOperation(value = "搜索")
    public Object search(String keyword) {
        if (null == keyword) {
            keyword = "";
        }
        List<Product> ps = productService.search(keyword, 0, 20);
        productImageService.setFirstProductImages(ps);
        productService.setSaleAndReviewNumber(ps);
        return ps;
    }

    private int buyOneAndAddCart(int pid, int num, HttpSession session) { // TODO
        Product product = productService.get(pid);
        int orderItemId = 0;
        User user = (User) session.getAttribute("user");
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getProduct().getId() == product.getId()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);
                found = true;
                orderItemId = orderItem.getId();
                break;
            }
        }
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setProduct(product);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            orderItemId = orderItem.getId();
        }
        return orderItemId;
    }

    @GetMapping("forebuyone")
    @ApiOperation(value = "立即购买")
    public Object buyOne(int pid, int num, HttpSession session) {
        return buyOneAndAddCart(pid, num, session);
    }

    @GetMapping("forebuy")
    @ApiOperation("结算")
    public Object buy(String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
        float total = 0;
        for (String strid : oiid) {
            int id = Integer.parseInt(strid);
            OrderItem oi = orderItemService.get(id);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            orderItems.add(oi);
        }
        productImageService.setFirstProductImagesOnOrderItems(orderItems);
        session.setAttribute("ois", orderItems);
        Map<String, Object> map = new HashMap<>();
        map.put("orderItems", orderItems);
        map.put("total", total);
        return Result.success(map);
    }

    @GetMapping("foreaddCart")
    @ApiOperation(value = "添加购物车")
    public Object addCart(int pid, int num, HttpSession session) {
        buyOneAndAddCart(pid, num, session);
        return Result.success();
    }

    @GetMapping("forecart")
    @ApiOperation(value = "购物车")
    public Object cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        productImageService.setFirstProductImagesOnOrderItems(ois);
        return ois;
    }


    @GetMapping("forechangeOrderItem")
    @ApiOperation(value = "修改购物车中某产品数量")
    public Object changeOrderItem(HttpSession session, int pid, int num) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == pid) {
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return Result.success();
    }

    @GetMapping("foredeleteOrderItem")
    @ApiOperation(value = "删除购物车中莫商品")
    public Object deleteOrderItem(HttpSession session, int oiid) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        orderItemService.delete(oiid);
        return Result.success();
    }

    @PostMapping("forecreateOrder")
    @ApiOperation(value = "由 orderItem 形成 order")
    public Object createOrder(@RequestBody Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +
                RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        float total = orderService.add(order, ois);
        Map<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);
        return Result.success(map);
    }

    @GetMapping("forepayed")
    @ApiOperation(value = "支付后更新订单状态")
    public Object payed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }

    @GetMapping("forebought")
    @ApiOperation(value = "某用户所有状态不为 deleted 的订单")
    public Object bought(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null == user) {
            return Result.fail("未登录");
        }
        List<Order> orders = orderService.listByUserWithoutDelete(user);
        orderService.removeOrderFromOrderItem(orders); // 调用该方法以防止返回的 json 数据中 order 和 orderItem 无限嵌套循环
        return orders;
    }

    @GetMapping("foreconfirmPay")
    @ApiOperation(value = "确认收货")
    public Object confirmPay(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
        orderService.removeOrderFromOrderItem(o);
        return o;
    }

    @GetMapping("foreorderConfirmed")
    @ApiOperation(value = "确认收货成功，订单状态变为待评价")
    public Object orderConfirmed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return Result.success();
    }

    @PutMapping("foredeleteOrder")
    @ApiOperation(value = "删除订单")
    public Object deleteOrder(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return Result.success();
    }
}
