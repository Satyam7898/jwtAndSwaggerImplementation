package com.example.demo.securityImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
//        // Create roles
//        Role roleUser = new Role();
//        roleUser.setName("ROLE_USER");
//        roleUser=roleRepository.save(roleUser);
//
//        // Create user
//        AppUser appUser = new AppUser();
//        appUser.setUsername("testUser");
//        appUser.setPassword(passwordEncoder.encode("password"));
//        appUser.setRoles(new HashSet<>(Set.of(roleUser)));
//
//        // Save user and roles
//        userRepository.save(appUser);


        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_ADMIN");

        // Optionally create an initial user
        createDefaultUser();
    }

    private void createRoleIfNotFound(String roleName) {
        if (!roleRepository.findByName(roleName).isPresent()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createDefaultUser() {
        if (userRepository.findByUsername("admin") == null) {
            AppUser appUser = new AppUser();
            appUser.setUsername("admin");
            appUser.setPassword(passwordEncoder.encode("adminPassword"));
            appUser.setRoles(new HashSet<>(Set.of(roleRepository.findByName("ROLE_ADMIN").get())));
            userRepository.save(appUser);
        }
    }
}
