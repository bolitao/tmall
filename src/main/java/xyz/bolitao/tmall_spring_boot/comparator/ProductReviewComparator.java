package xyz.bolitao.tmall_spring_boot.comparator;

import xyz.bolitao.tmall_spring_boot.pojo.Product;

import java.util.Comparator;

/**
 * @author 陶波利
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount() - p1.getReviewCount();
    }
}