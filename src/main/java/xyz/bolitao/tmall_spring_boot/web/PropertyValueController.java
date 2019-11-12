package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.PropertyValue;
import xyz.bolitao.tmall_spring_boot.service.ProductService;
import xyz.bolitao.tmall_spring_boot.service.PropertyValueService;

import java.util.List;

/**
 * @author 陶波利
 */
@Api(tags = "admin 产品属性值")
@RestController
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;

    @GetMapping("/products/{pid}/propertyValues")
    @ApiOperation(value = "返回产品的所有 property value")
    public List<PropertyValue> list(@PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        return propertyValues;
    }

    @PutMapping("/propertyValues")
    @ApiOperation(value = "修改产品的属性值")
    public Object update(@RequestBody PropertyValue bean) throws Exception {
        propertyValueService.update(bean);
        return bean;
    }
}
