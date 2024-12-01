package org.project.service;

import org.project.entity.Cart;
import org.project.entity.Product;
import org.project.repository.CartRepository;
import org.project.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }


    public Cart addProductToCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }

        cart.getProducts().add(product);
        cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getProducts().remove(product);
        cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());
        return cartRepository.save(cart);
    }

    public Cart emptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        cart.getProducts().clear();
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }
}