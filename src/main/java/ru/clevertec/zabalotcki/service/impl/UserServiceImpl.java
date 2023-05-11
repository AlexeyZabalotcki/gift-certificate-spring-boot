package ru.clevertec.zabalotcki.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.zabalotcki.dao.UserRepository;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.exception.UserNotFoundException;
import ru.clevertec.zabalotcki.mapper.UserMapper;
import ru.clevertec.zabalotcki.model.User;
import ru.clevertec.zabalotcki.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Check user id " + id)))
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toListDto(userRepository.findAll());
    }

    @Override
    public UserDto save(UserDto userDto) {
        User saved = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(saved);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public String findTag(Long userId) {
        List<String> result = userRepository.findTag(userId);
        return result.get(0);
    }

    @Override
    public User findByIdEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("check user id"));
    }
}
