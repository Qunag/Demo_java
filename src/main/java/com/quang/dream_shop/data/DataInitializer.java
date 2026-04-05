package com.quang.dream_shop.data;


import com.quang.dream_shop.model.Role;
import com.quang.dream_shop.model.User;
import com.quang.dream_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String > defaultRoles = Set.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR");
        createDefaultUserIfNotExists();
        createDefaultRolesIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }



    private void createDefaultRolesIfNotExists(Set<String> defaultRoles) {
        defaultRoles.stream()
                .filter(role -> roleRepository.findByRoleName(role).isEmpty())
                .map(Role::new).forEach(roleRepository ::save);

    }



    private void createDefaultUserIfNotExists() {
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


    private void createDefaultAdminIfNotExists() {

        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        for (int i = 1; i <= 6; i++) {
            String defaultEmail = "admin" + i + "@example.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin ");
            user.setLastName("Admin " + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default Admin: " + user.getEmail());

        }
    }
    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
