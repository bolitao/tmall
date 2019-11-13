package xyz.bolitao.tmall_spring_boot.comparator;

import xyz.bolitao.tmall_spring_boot.pojo.Product;

import java.util.Comparator;

/**
 * @author 陶波利
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}