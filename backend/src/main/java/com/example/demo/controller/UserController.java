package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController  // 等价于 @Controller + @ResponseBody
@RequestMapping("/api/users")  // 所有接口的公共路径
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 查询所有用户
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // 新增用户
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // 修改用户（根据 id）
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);  // 确保 id 一致
        return userRepository.save(user);
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}