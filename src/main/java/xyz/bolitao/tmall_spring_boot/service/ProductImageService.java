package xyz.bolitao.tmall_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.bolitao.tmall_spring_boot.dao.ProductImageDAO;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.ProductImage;

import java.util.List;


/**
 * @author boli
 */
@Service
public class ProductImageService {
    public static final String TYPE_SINGLE = "single";
    public static final String TYPE_DETAIL = "detail";

    @Autowired
    ProductImageDAO productImageDAO;
    @Autowired
    ProductService productService;

    public void add(ProductImage bean) {
        productImageDAO.save(bean);

    }

    public void delete(int id) {
        productImageDAO.deleteById(id);
    }

    public ProductImage get(int id) {
        return productImageDAO.findById(id).get();
    }

    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, TYPE_SINGLE);
    }

    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, TYPE_DETAIL);
    }

//    public void setFirstProductImage(Product product) {
//        List<ProductImage> singleImages = listSingleProductImages(product);
//        if (!singleImages.isEmpty()) {
//            product.setFirstProductImage(singleImages.get(0));
//        } else {
//            // 这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片
//            product.setFirstProductImage(new ProductImage());
//        }
//
//    }

    public void setFirstProductImages(List<Product> products) {
        for (Product product : products) {
//            setFirstProductImage(product);
        }
    }

}