package ru.clevertec.zabalotcki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.zabalotcki.dto.UserDto;
import ru.clevertec.zabalotcki.exception.OrderNotFoundException;
import ru.clevertec.zabalotcki.exception.UserNotFoundException;
import ru.clevertec.zabalotcki.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> all = userService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable("id") Long id) {
        UserDto userDto;
        try {
            userDto = userService.findById(id);
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check user id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        UserDto saved = userService.save(userDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/tag")
    public ResponseEntity<String> findTag(@RequestParam("userId") Long userId) {
        String tag;
        try {
            tag = userService.findTag(userId);
        } catch (OrderNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check order id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDto> deleteById(@PathVariable Long id) {
        try {
            userService.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            ex.printStackTrace();
            return new ResponseEntity("That id: " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
