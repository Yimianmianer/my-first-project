package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // 启用 Mockito 支持
class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // 模拟 Repository

    @InjectMocks
    private UserServiceImpl userService;    // 注入模拟的 Repository 到 Service 实现类

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("张三", 25, "zhangsan@test.com");
        testUser.setId(1L);
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // 准备模拟数据
        List<User> mockUsers = Arrays.asList(testUser, new User("李四", 30, "lisi@test.com"));
        when(userRepository.findAll()).thenReturn(mockUsers);

        // 执行方法
        List<User> result = userService.findAll();

        // 验证结果
        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void save_ShouldReturnSavedUser() {
        // 准备模拟行为
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // 执行
        User saved = userService.save(testUser);

        // 验证
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void update_ShouldSetIdAndSave() {
        Long existingId = 1L;
        User updatedInfo = new User("张三丰", 26, "zhangsf@test.com");
        updatedInfo.setId(existingId);
        when(userRepository.save(any(User.class))).thenReturn(updatedInfo);

        User result = userService.update(existingId, updatedInfo);

        assertEquals(existingId, result.getId());
        assertEquals("张三丰", result.getName());
        verify(userRepository).save(updatedInfo);
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);

        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}