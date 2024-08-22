package com.book.store.svc.services;

import com.book.store.svc.BaseIntegrationTest;
import com.book.store.svc.commons.exceptions.ServiceValidationException;
import com.book.store.svc.commons.utils.DecimalUtil;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.db.models.BookProduct;
import com.book.store.svc.domains.book.db.repos.AuthorRepo;
import com.book.store.svc.domains.book.db.repos.BookProductRepo;
import com.book.store.svc.domains.book.db.repos.BookRepo;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.cart.db.models.CartItem;
import com.book.store.svc.domains.cart.db.repos.CartRepo;
import com.book.store.svc.domains.cart.dtos.CartItemRequest;
import com.book.store.svc.domains.cart.dtos.CreateCartRequest;
import com.book.store.svc.domains.cart.services.CartService;
import com.book.store.svc.domains.customer.db.models.Customer;
import com.book.store.svc.domains.customer.db.repos.CustomerRepo;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CartServiceTest extends BaseIntegrationTest {

    @Autowired
    protected CartService cartService;
    @Autowired
    protected BookRepo bookRepo;
    @Autowired
    protected BookProductRepo bookProductRepo;
    @Autowired
    protected AuthorRepo authorRepo;
    @Autowired
    protected CustomerRepo customerRepo;

    @Autowired
    protected CartRepo cartRepo;

    protected Customer customer = null;
    protected Book book = null;
    protected BookProduct bookProduct1;
    protected BookProduct bookProduct2;

    @SneakyThrows
    @Before
    public void init(){
        customer = new Customer("Yousuf","James", "ogbosukajames@gmail.com", "VI");
        customerRepo.save(customer);
        Author author = createAuthor();
        book = new Book("There was a country", "123-490", author, EGenre.Poetry, LocalDate.now());
        bookRepo.save(book);
        bookProduct1 = new BookProduct(book, BigDecimal.valueOf(300), "Hot Sales", BigDecimal.ZERO, "Emotional and Psychology");
        bookProduct2 = new BookProduct(book, BigDecimal.valueOf(300), "Regular Sales", BigDecimal.ZERO, "Emotional and Psychology");
        bookProductRepo.save(bookProduct1);
        bookProductRepo.save(bookProduct2);
    }

    @After
    @SneakyThrows
    public void tearDown(){
        cartRepo.deleteAll();
        bookProductRepo.deleteAll();
    }

    @Test
    @SneakyThrows
    public void createCartTest_should_succeed_when_data_valid(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        // act
        var cart = cartService.createCart(customer.getCode(), cartRequest);

        // assert
        Assert.assertNotNull(cart);
        Assert.assertEquals(cart.cartItems.stream().count(), 2);
        Assert.assertEquals(cart.getTotal(), computeTotal(cart.cartItems));
    }

    @Test(expected = ServiceValidationException.class)
    @SneakyThrows
    public void createCartTest_should_throw_exception_when_has_pending_cart(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        var cart = cartService.createCart(customer.getCode(), cartRequest);

        // act and expect throw
        var car2 = cartService.createCart(customer.getCode(), cartRequest);
    }

    @Test
    @SneakyThrows
    public void addItemToCartTest_should_update_cart_items_and_total(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        var cart = cartService.createCart(customer.getCode(), cartRequest);
        BigDecimal initialCartTotal = cart.getTotal();


        // add new product
        var newlyAddedProductItem = new BookProduct(book, BigDecimal.valueOf(1000), "American Sale Pack", BigDecimal.ZERO, "Emotional and Psychology");
        bookProductRepo.save(newlyAddedProductItem);

        //act
        var cartUpdated = cartService.addItemToCart(cart.getCode(), new CartItemRequest(newlyAddedProductItem.getCode(), 1));


        // assert
        Assert.assertNotNull(cartUpdated);
        Assert.assertEquals(cartUpdated.getTotal(),
                initialCartTotal.add(newlyAddedProductItem.getPrice()));
        Assert.assertEquals(cartUpdated.cartItems.stream().count(), 3);
    }

    @Test
    @SneakyThrows
    public void addItemToCartTest_should_update_cart_total_but_cart_items_count_remain_same(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        var cart = cartService.createCart(customer.getCode(), cartRequest);
        BigDecimal initialCartTotal = cart.getTotal();

        //act
        var cartUpdated = cartService.addItemToCart(cart.getCode(), new CartItemRequest(bookProduct1.getCode(), 1));

        // assert
        Assert.assertNotNull(cartUpdated);
        Assert.assertNotEquals(cartUpdated.getTotal(), initialCartTotal);
        Assert.assertEquals(cartUpdated.getTotal(), initialCartTotal.subtract(bookProduct1.getPrice()));

    }

    @Test
    @SneakyThrows
    public void removeItemFromCart_should_remove_item_and_update_cart_total(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        var cart = cartService.createCart(customer.getCode(), cartRequest);
        BigDecimal initialCartTotal = cart.getTotal();

        //act
        var cartUpdated = cartService.removeItemFromCart(cart.getCode(), cart.cartItems.stream().findFirst().get().getCode());

        // assert
        Assert.assertNotNull(cartUpdated);
        Assert.assertNotEquals(cartUpdated.getTotal(), initialCartTotal);
    }


    @Test
    @SneakyThrows
    public void getCartDetailsForCustomer_return_cart_details(){
        // arrange
        List<CartItemRequest> cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        var cartRequest = new CreateCartRequest(customer.getCode(), cartItems);
        cartService.createCart(customer.getCode(), cartRequest);

        // act
        var result = cartService.getCartForCustomer(customer.getCode());

        // assert
        Assert.assertNotNull(result);
        Assert.assertEquals(result.cartItems.stream().count(), cartRequest.getCartItems().stream().count());

    }


    // RE-USEABLES
    private Author createAuthor(){
        Author author =  new Author();
        author.setFirstName("John");
        author.setLastName("Abayomi");
        author.setOtherInfo("Romance novel writer");
        authorRepo.save(author);
        return  author;
    }

    private BigDecimal computeTotal(Set<CartItem> cartItems){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem: cartItems){
            if(Objects.isNull(cartItem.getProduct()))
                throw new RuntimeException("Cart item's product detail cannot be null when computing total price");
            totalPrice = totalPrice.add(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return DecimalUtil.standardRound(totalPrice);
    }
}
