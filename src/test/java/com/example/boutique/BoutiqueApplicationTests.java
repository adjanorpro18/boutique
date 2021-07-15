package com.example.boutique;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BoutiqueApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
   void EncoderPassword(){
        User u = userRepository.findById(1L).get();
        String password = new BCryptPasswordEncoder().encode("beanpassword");
        u.setPassword(password);
        userRepository.save(u);
    }

}
