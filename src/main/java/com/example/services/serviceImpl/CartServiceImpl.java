package com.example.services.serviceImpl;

import com.example.Exception.CartNotFoundException;
import com.example.Exception.FoodItemNotFoundException;
import com.example.Exception.QuantityException;
import com.example.Exception.UserIdNotFoundException;
import com.example.entities.Cart;
import com.example.entities.CartItem;
import com.example.entities.FoodItem;
import com.example.entities.User;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.FoodItemRepository;
import com.example.repository.UserRepository;
import com.example.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;
    @Override
    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new
                        UserIdNotFoundException("User not found" + userId));
        return cartRepository.findByUser(user);
    }

//    @Override
//    public Cart addFoodToCart(Long userId, Long foodId, int quantity) {
//        if (quantity <= 0) {
//            throw new QuantityException("Quantity must be greater than zero");
//        }
//        // 1. Get user cart (create if not exists)
//        User user = userRepository.
//                findById(userId).orElseThrow(() ->
//                        new UserIdNotFoundException("User not found" + userId));
//
//        // 2. Get food item
//        FoodItem foodItem = foodItemRepository.findById(foodId).
//                orElseThrow(() ->
//                        new FoodItemNotFoundException("Food item with id not found.." + foodId));
//        Cart cart = cartRepository.findByUser(user);
//        if (cart == null) {
//            cart = new Cart();
//            cart.setUser(user);
//            cart.setQuantity(0);
//            cart.setTotalAmount(0);
//            cart = cartRepository.save(cart);  // Save new cart first
//        }
//
//
//        // 3. Check if food already in cart
//        CartItem cartItem = cartItemRepository.findByCartAndFoodItem(cart, foodItem).orElse(null);
//        if(cartItem!= null){
//            // Increase quantity
//            cartItem.setQuantity(cartItem.getQuantity()+quantity);
//            System.out.println("cart item quantity " + cartItem.getQuantity());
//            cartItemRepository.save(cartItem);
//        }else {
////            cartItem = new CartItem();
////            cartItem.setFoodItem(foodItem);
////            cartItem.setQuantity(quantity);
////            cartItem.setPrice(foodItem.getPrice());
////            cartItem.setCart(cart);
////            cart.getCartItems().add(cartItem);
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setFoodItem(foodItem);
//            cartItem.setQuantity(quantity);
//            cartItem.setPrice(foodItem.getPrice());
//            cartItem = cartItemRepository.save(cartItem);  // Save explicitly
//            cart.getCartItems().add(cartItem);
//
//
//        }
//        // 4. Recalculate cart
//        recalculateCart(cart);
//
//        return cartRepository.save(cart);
//    }

    @Override
    public Cart addFoodToCart(Long userId, Long foodId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        FoodItem foodItem = foodItemRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        // Find cart by user
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            // Create a new cart if it doesn't exist
            cart = new Cart();
            cart.setUser(user);
            cart.setQuantity(0);
            cart.setTotalAmount(0);
        }

        // Check if the item already exists in the cart
        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getFoodItem().getFoodId().equals(foodId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(cartItem.getPrice() + foodItem.getPrice() * quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setFoodItem(foodItem);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(foodItem.getPrice() * quantity);
            cart.getCartItems().add(cartItem);
        }

        // Update cart totals
        int totalQuantity = cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
        double totalAmount = cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum();

        cart.setQuantity(totalQuantity);
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }


    @Override
    public Cart removeFoodFromCart(Long userId, Long foodId) {
        User user = userRepository.
                findById(userId).orElseThrow(() ->
                        new UserIdNotFoundException("User not found" + userId));

        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        CartItem cartItem = cart.getCartItems() //Gets the list of CartItem objects from the cart.
                .stream()
                .filter(items-> //Checks each cart item
                        items.getFoodItem().getFoodId().equals(foodId)) //Compares the foodId inside the CartItem with the given foodId

                .findFirst()
                .orElseThrow(()->new FoodItemNotFoundException("Food Item Not found"));


        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        recalculateCart(cart);


        return cartRepository.save(cart);
    }

//    @Override
//    public Cart clearCart(Long userId) {
////        User user = userRepository.
////                findById(userId).orElseThrow(() ->
////                        new UserIdNotFoundException("User not found" + userId));
////
////        Cart cart = cartRepository.findByUser(user);
////
////        if (cart == null) {
////            throw new RuntimeException("Cart not found for user");
////        }
////        cartItemRepository.deleteAll(cart.getCartItems());
////        cart.getCartItems().clear();
////
////        cart.setQuantity(0);
////        cart.setTotalAmount(0);
////        return cartRepository.save(cart);
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserIdNotFoundException("User not found: " + userId));
//
//        Cart cart = cartRepository.findByUser(user);
//        if (cart == null) {
//            throw new CartNotFoundException("Cart not found for user: " + userId);
//        }
//
//        // 1️⃣ Delete all cart items from database
//        if (!cart.getCartItems().isEmpty()) {
//            cartItemRepository.deleteAll(cart.getCartItems());
//        }
//
//        // 2️⃣ Clear the in-memory list
//        cart.getCartItems().clear();
//
//        // 3️⃣ Reset totals
//        cart.setQuantity(0);
//        cart.setTotalAmount(0);
//
//        // 4️⃣ Persist the cart to DB
//        cart = cartRepository.save(cart);
//
//        // 5️⃣ Detach the cart to avoid stale persistence context issues (optional but safe)
//        cartRepository.flush();
//
//        return cart;
//    }

    @Transactional
    @Override
    public Cart clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserIdNotFoundException("User not found: " + userId));

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for user: " + userId);
        }

        // 🔥 DELETE requires transaction
        cartItemRepository.deleteByCart(cart);

        // Clear persistence context collection
        cart.getCartItems().clear();

        // Reset totals
        cart.setQuantity(0);
        cart.setTotalAmount(0);

        return cartRepository.save(cart);
    }


    @Override
    public void recalculateCart(Cart cart) {
    int totalQuantity = 0;
    double totalAmount = 0;

    for(CartItem foodItem : cart.getCartItems()){
        totalQuantity += foodItem.getQuantity();
        totalAmount += foodItem.getPrice()* foodItem.getQuantity();
        cart.setQuantity(totalQuantity);
        cart.setTotalAmount(totalAmount);
    }
    }
//    private void clearCartItemsInternal(Cart cart) {
//        cartItemRepository.deleteByCart(cart);
//        cart.getCartItems().clear();
//        cart.setQuantity(0);
//        cart.setTotalAmount(0);
//    }
}
