package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void add_to_cart_happy_path() throws Exception {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        r.setItemId(1L);
        r.setQuantity(1);

        Cart c = new Cart();
        c.setTotal(new BigDecimal(0));

        Item item = new Item();
        item.setPrice(new BigDecimal(1));
        User u = new User();
        u.setUsername("test");
        u.setCart(c);
        u.setPassword("testPassword");

        when(userRepository.findByUsername("test")).thenReturn(u);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> response = cartController.addTocart(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_happy_path() throws Exception {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setUsername("test");
        r.setItemId(1L);
        r.setQuantity(1);

        Cart c = new Cart();
        c.setTotal(new BigDecimal(0));

        Item item = new Item();
        item.setPrice(new BigDecimal(1));
        User u = new User();
        u.setUsername("test");
        u.setCart(c);
        u.setPassword("testPassword");

        when(userRepository.findByUsername("test")).thenReturn(u);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        final ResponseEntity<Cart> response = cartController.removeFromcart(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
}
