package ru.clevertec.zabalotcki.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.zabalotcki.dto.OrderDto;
import ru.clevertec.zabalotcki.dto.OrderInfoDto;
import ru.clevertec.zabalotcki.exception.OrderNotFoundException;
import ru.clevertec.zabalotcki.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> findAll() {
        List<OrderDto> all = orderService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> findById(@PathVariable("id") Long id) {
        OrderDto orderDto;
        try {
            orderDto = orderService.findById(id);
        } catch (OrderNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check order id", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/find")
    public ResponseEntity<List<OrderDto>> findByUser(@RequestParam("userId") Long userId) {
        List<OrderDto> orderDto;
        try {
            orderDto = orderService.findByUser(userId);
        } catch (OrderNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check user id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<OrderInfoDto> findOrderParams(@PathVariable("id") Long id) {
        OrderInfoDto orderDto;
        try {
            orderDto = orderService.findInfo(id);
        } catch (OrderNotFoundException ex) {
            ex.printStackTrace();
            return new ResponseEntity("Check order id", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(orderDto);
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDto> createOrder(@RequestParam("userId") Long userId,
                                                @RequestParam("certId") Long certId) {
        OrderDto saved = orderService.createOrder(userId, certId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
