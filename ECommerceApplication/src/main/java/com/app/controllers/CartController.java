package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.payloads.CartDTO;
import com.app.services.CartService;
import com.app.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce Application")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private CartService cartService;

	@PostMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId,
			@PathVariable Integer quantity) {
		CartDTO cartDTO = cartService.addProductToCart(cartId, productId, quantity);

		log.info("Product added successfully!");
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
	}

	@GetMapping("/admin/carts")
	public ResponseEntity<List<CartDTO>> getCarts() {

		List<CartDTO> cartDTOs = cartService.getAllCarts();
		log.info("Product retrieved successfully!");
		return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND);
	}

	@GetMapping("/public/users/{emailId}/carts/{cartId}")
	public ResponseEntity<CartDTO> getCartById(@PathVariable String emailId, @PathVariable Long cartId) {
		CartDTO cartDTO = cartService.getCart(emailId, cartId);

		log.info("Cart retrieved successfully!");

		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.FOUND);
	}

	@PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
	public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long cartId, @PathVariable Long productId,
			@PathVariable Integer quantity) {
		CartDTO cartDTO = cartService.updateProductQuantityInCart(cartId, productId, quantity);
		log.info("Product updated successfully!");
		return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
	}

	@DeleteMapping("/public/carts/{cartId}/product/{productId}")
	public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
		String status = cartService.deleteProductFromCart(cartId, productId);

		log.info("Product deleted successfully!");

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
