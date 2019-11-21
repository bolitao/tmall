package xyz.bolitao.tmall_spring_boot.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.ProductImage;
import xyz.bolitao.tmall_spring_boot.service.CategoryService;
import xyz.bolitao.tmall_spring_boot.service.ProductImageService;
import xyz.bolitao.tmall_spring_boot.service.ProductService;
import xyz.bolitao.tmall_spring_boot.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 陶波利
 */
@RestController
@Api(tags = "admin 产品图片")
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    CategoryService categoryService;

    /**
     * 获得某产品图片
     *
     * @param type type
     * @param pid  productID
     * @return product images
     * @throws Exception exception
     */
    @GetMapping("/products/{pid}/productImages")
    @ApiOperation(value = "获得某产品图片")
    public List<ProductImage> list(@RequestParam("type") String type, @PathVariable("pid") int pid) throws Exception {
        Product product = productService.get(pid);
        switch (type) {
            case ProductImageService.TYPE_SINGLE:
                return productImageService.listSingleProductImages(product);
            case ProductImageService.TYPE_DETAIL:
                return productImageService.listDetailProductImages(product);
            default:
                return new ArrayList<>();
        }
    }

    /**
     * 上传图片
     *
     * @param pid     productID
     * @param type    image type
     * @param image   image file
     * @param request http servlet request
     * @return ProductImage object
     * @throws Exception exception
     */
    @PostMapping("/productImages")
    @ApiOperation(value = "上传图片")
    public Object add(@RequestParam("pid") int pid, @RequestParam("type") String type, MultipartFile image,
                      HttpServletRequest request) throws Exception {
        ProductImage productImage = new ProductImage();
        Product product = productService.get(pid);
        productImage.setProduct(product);
        productImage.setType(type);
        productImageService.add(productImage);
        String folder = "img/";
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        String fileName = file.getName();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) { // 如果上传的图片类型为 single 对其进行缩放另存
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }
        return productImage;
    }

    /**
     * 删除图片
     *
     * @param id      image id
     * @param request HttpServletRequest
     * @return null
     * @throws Exception exception
     */
    @DeleteMapping("/productImages/{id}")
    @ApiOperation(value = "删除图片")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) throws Exception {
        ProductImage productImage = productImageService.get(id);
        productImageService.delete(id);
        String folder = "img/";
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            folder += "productSingle";
        } else {
            folder += "productDetail";
        }
        File imageFolder = new File(request.getServletContext().getRealPath(folder));
        File file = new File(imageFolder, productImage.getId() + ".jpg");
        String fileName = file.getName();
        file.delete();
        if (ProductImageService.TYPE_SINGLE.equals(productImage.getType())) {
            String imageFolder_small = request.getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getServletContext().getRealPath("img/productSingle_middle");
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }
        return null;
    }
}
