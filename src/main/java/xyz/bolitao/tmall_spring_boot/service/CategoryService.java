package xyz.bolitao.tmall_spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.bolitao.tmall_spring_boot.dao.CategoryDAO;
import xyz.bolitao.tmall_spring_boot.pojo.Category;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.util.Page4Navigator;

import java.util.List;

/**
 * @author 陶波利
 */
@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    @Cacheable(key = "'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page pageFromJPA = categoryDAO.findAll(pageable);
        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @CacheEvict(allEntries = true)
    public void add(Category bean) {
        categoryDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        categoryDAO.deleteById(id);
    }

    @Cacheable(key = "'categories-one-'+ #p0")
    public Category get(int id) {
        return categoryDAO.findById(id).get();
    }

    @CacheEvict(allEntries = true)
    public void update(Category bean) {
        categoryDAO.save(bean);
    }

    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }

    public void removeCategoryFromProduct(Category category) {
        List<Product> products = category.getProducts();
        if (null != products) {
            for (Product product : products) {
                product.setCategory(null);
            }
        }
        List<List<Product>> productsByRow = category.getProductsByRow();
        if (null != productsByRow) {
            for (List<Product> productList : productsByRow) {
                for (Product product : productList) {
                    product.setCategory(null);
                }
            }
        }
    }
}