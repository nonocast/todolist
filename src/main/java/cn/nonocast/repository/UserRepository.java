package cn.nonocast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cn.nonocast.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);
}
