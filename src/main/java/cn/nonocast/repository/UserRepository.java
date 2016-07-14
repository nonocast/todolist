package cn.nonocast.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import cn.nonocast.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByWechatid(String wechatid);
    Page<User> findByRole(User.Role role, Pageable pageable);

    @Query(value = "SELECT p FROM User p WHERE LOWER(p.email) LIKE LOWER(concat('%',:q,'%')) OR LOWER(p.name) LIKE LOWER(concat('%',:q,'%'))")
    Page<User> findByKeyword(@Param("q") String q, Pageable pageable);

//    Page<User> findByNameIgnoreCaseContaining(String q, Pageable pageable);

//    @Query("SELECT p FROM User p WHERE p.role = :role AND (p.email LIKE %:q% OR p.name LIKE %:q%)")
//    Page<User> findByRole(@Param("role") User.Role role, @Param("q") String q, Pageable p);
}