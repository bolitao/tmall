package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.Product;
import xyz.bolitao.tmall_spring_boot.pojo.Property;
import xyz.bolitao.tmall_spring_boot.pojo.PropertyValue;

import java.util.List;

/**
 * @author 陶波利
 */
public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {
    /**
     * 查找某产品的属性值
     *
     * @param product product
     * @return property values
     */
    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    /**
     * 根据产品和属性获得某属性值
     *
     * @param property property
     * @param product  product
     * @return PropertyValue
     */
    PropertyValue getByPropertyAndProduct(Property property, Product product);
}
