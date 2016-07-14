package cn.nonocast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.nonocast.repository.*;
import cn.nonocast.model.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
