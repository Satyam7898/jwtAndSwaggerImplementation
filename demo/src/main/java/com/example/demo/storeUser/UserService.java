package com.example.demo.storeUser;

import com.example.demo.securityImple.AppUser;
import com.example.demo.securityImple.Role;
import com.example.demo.securityImple.RoleRepository;
import com.example.demo.securityImple.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserDTO userDTO) {
        // Check if user already exists
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }

        // Create the user
        AppUser appUser = new AppUser();
        appUser.setUsername(userDTO.getUsername());
        appUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Set roles, providing a default role if none are specified
        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            roles.add(defaultRole);
        } else {
            for (String roleName : userDTO.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
        }
        appUser.setRoles(roles);

        // Save the user
        userRepository.save(appUser);
    }
}
