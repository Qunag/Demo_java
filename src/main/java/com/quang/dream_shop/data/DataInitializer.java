package com.quang.dream_shop.data;


import com.quang.dream_shop.model.User;
import com.quang.dream_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
            createDefaultUserIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        for(int i = 1  ; i<=5 ; i++){
            String email = "user" + i + "@example.com";
            if (!userRepository.existsByEmail(email)) {
                var user = new User();
                user.setFirstName("User" + i);
                user.setLastName("Example");
                user.setEmail(email);
                user.setPassword("123456");
                userRepository.save(user);
                System.out.println("Deafault user created: " + i +"with " + email );
            }
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
