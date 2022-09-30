

package com.example.lostapp.controller;

import com.example.lostapp.exception.ResourceNotFoundException;
import com.example.lostapp.model.User;
import com.example.lostapp.repository.UserRepository;
import com.example.lostapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserService userService;

    /**
     * Get all users list.
     *
     * @return the list
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets users by id.
     *
     * @param username the user id
     * @return the users by id
     * @throws ResourceNotFoundException the resource not found exception
     */

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(String.valueOf(username));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> loginByUserName(@RequestBody User user) throws ClassNotFoundException {
        User cUser = userService.login(user);
        return ResponseEntity.ok().body(cUser);
    }

    /**
     * Create user user.
     *
     * @param user the user
     * @return the user
     */
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * Update user response entity.
     *
     * @param userId      the user id
     * @param userDetails the user details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        user.setUsername(userDetails.getUsername());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setRole(userDetails.getRole());
        user.setPassword(userDetails.getPassword());


        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete user map.
     *
     * @param userId the user id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/user/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
