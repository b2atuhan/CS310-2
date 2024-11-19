package edu.sabanciuniv.week7day1.service;

import edu.sabanciuniv.week7day1.model.User;
import edu.sabanciuniv.week7day1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
