package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.bolitao.tmall_spring_boot.pojo.Order;
import xyz.bolitao.tmall_spring_boot.service.OrderItemService;
import xyz.bolitao.tmall_spring_boot.service.OrderService;
import xyz.bolitao.tmall_spring_boot.util.Page4Navigator;
import xyz.bolitao.tmall_spring_boot.util.Result;

import java.util.Date;

/**
 * TODO: LJ
 *
 * @author 陶波利
 */
@RestController
@Api(tags = "admin 订单相关")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/orders")
    @ApiOperation(value = "订单信息（包含分页信息）")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Order> page = orderService.list(start, size, 5);
        orderItemService.fill(page.getContent());
        orderService.removeOrderFromOrderItem(page.getContent());
        return page;
    }

    @PutMapping("deliveryOrder/{oid}")
    @ApiOperation(value = "将订单状态改为已发货")
    public Object deliveryOrder(@PathVariable int oid) {
        Order o = orderService.get(oid);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        return Result.success();
    }
}