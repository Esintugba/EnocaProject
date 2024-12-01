package org.project.controller;

import org.project.entity.Cart;
import org.project.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }
    @PostMapping("/{customerId}/add/{productId}")
    public ResponseEntity<Cart> addProductToCart(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.addProductToCart(customerId, productId, quantity));
    }

    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeProductFromCart(customerId, productId));
    }

    @PostMapping("/{customerId}/empty")
    public ResponseEntity<Cart> emptyCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.emptyCart(customerId));
    }
}