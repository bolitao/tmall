package xyz.bolitao.tmall_spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.bolitao.tmall_spring_boot.pojo.User;

/**
 * @author 陶波利
 */
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByName(String name);
}
