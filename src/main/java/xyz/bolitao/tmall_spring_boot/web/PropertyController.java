package xyz.bolitao.tmall_spring_boot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.bolitao.tmall_spring_boot.pojo.Property;
import xyz.bolitao.tmall_spring_boot.service.PropertyService;
import xyz.bolitao.tmall_spring_boot.util.Page4Navigator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 陶波利
 */
@RestController
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid, @RequestParam(value = "start",
            defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start < 0 ? 0 : start;
        Page4Navigator<Property> page = propertyService.list(cid, start, size, 5);
        return page;
    }

    /**
     * 点击编辑按钮后返回数据给页面
     *
     * @param id 属性 id
     * @return Property bean
     * @throws Exception
     */
    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
        return propertyService.get(id);
    }

    /**
     * post 方式新增属性
     *
     * @param bean 从 axios 取到的 Property bean
     * @return 新增的属性
     * @throws Exception
     */
    @PostMapping("/properties")
    public Object add(@RequestBody Property bean) throws Exception {
        propertyService.add(bean);
        return bean;
    }

    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        propertyService.delete(id);
        return null;
    }

    @PutMapping("/properties")
    public Object update(@RequestBody Property bean) throws Exception {
        propertyService.update(bean);
        return bean;
    }
}
