package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.bolitao.tmall_spring_boot.pojo.Category;
import xyz.bolitao.tmall_spring_boot.service.CategoryService;
import xyz.bolitao.tmall_spring_boot.util.ImageUtil;
import xyz.bolitao.tmall_spring_boot.util.Page4Navigator;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author boli
 */
@RestController
@Api(tags = "分类相关")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Page4Navigator<Category> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                         @RequestParam(value = "size", defaultValue = "5") int size) {
        start = start < 0 ? 0 : start;
        Page4Navigator<Category> navigator = categoryService.list(start, size, 5);
        return navigator;
    }

    /**
     * TODO
     * post 方式新增分类
     *
     * @param bean    bean
     * @param image   image file
     * @param request HttpServletRequest
     * @return added bean
     * @throws Exception
     */
    @PostMapping("/categories")
    public Object add(Category bean, MultipartFile image, HttpServletRequest request) throws Exception {
        categoryService.add(bean);
        saveOrUpdateImageFile(bean, image, request);
        return bean;
    }

    /**
     * 保存照片
     * TODO
     *
     * @param bean    bean
     * @param image   image
     * @param request request
     * @throws IOException
     */
    public void saveOrUpdateImageFile(Category bean, MultipartFile image, HttpServletRequest request)
            throws IOException {
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, bean.getId() + ".jpg"); // 使用 ID 键做文件名
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file); // 转为 jpg
        ImageIO.write(img, "jpg", file);
    }

    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        categoryService.delete(id);
        File imageFolder = new File(request.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id + ".jpg");
        file.delete();
        return null;
    }

    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) {
        return categoryService.get(id);
    }

    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        bean.setName(name);
        categoryService.update(bean);
        if (image != null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }
}