package com.book.store.svc.services;

import com.book.store.svc.BaseIntegrationTest;
import com.book.store.svc.domains.book.db.models.Author;
import com.book.store.svc.domains.book.db.models.Book;
import com.book.store.svc.domains.book.db.models.BookProduct;
import com.book.store.svc.domains.book.db.repos.AuthorRepo;
import com.book.store.svc.domains.book.db.repos.BookProductRepo;
import com.book.store.svc.domains.book.db.repos.BookRepo;
import com.book.store.svc.domains.book.enums.EGenre;
import com.book.store.svc.domains.cart.db.models.Cart;
import com.book.store.svc.domains.cart.db.repos.CartRepo;
import com.book.store.svc.domains.cart.dtos.CartItemRequest;
import com.book.store.svc.domains.cart.dtos.CreateCartRequest;
import com.book.store.svc.domains.cart.services.CartService;
import com.book.store.svc.domains.checkOut.dtos.CheckOutRequest;
import com.book.store.svc.domains.checkOut.enums.EPaymentChannel;
import com.book.store.svc.domains.checkOut.repos.CheckoutRepo;
import com.book.store.svc.domains.checkOut.repos.TransactionRepo;
import com.book.store.svc.domains.checkOut.services.CheckoutService;
import com.book.store.svc.domains.customer.db.models.Customer;
import com.book.store.svc.domains.customer.db.repos.CustomerRepo;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CheckoutServiceTest extends BaseIntegrationTest {

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
    private CheckoutService checkoutService;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private CheckoutRepo checkoutRepo;

    @Autowired
    protected CartRepo cartRepo;
    private Cart cart;

    @Before
    @SneakyThrows
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

        // arrange
        var cartItems = List.of(
                new CartItemRequest(bookProduct1.getCode(), 2),
                new CartItemRequest(bookProduct2.getCode(), 4));

        cart = cartService.createCart(customer.getCode(), new CreateCartRequest(customer.getCode(), cartItems));
    }

    @After
    public void tearDown(){
        checkoutRepo.deleteAll();
        cartRepo.deleteAll();
        bookProductRepo.deleteAll();
    }

    @Test
    @SneakyThrows
    public void createCheckoutTest_should_check_out_and_create_transaction(){
        // arrange
        var checkOutRequest = new CheckOutRequest(EPaymentChannel.WEB, cart.getCode(), "Testing checkout");
        var customerCode = customer.getCode();

        // act
        var checkOutResult = checkoutService.createCheckout(customerCode, checkOutRequest);

        // assert
        var consequentialTransaction = transactionRepo.findByCustomerInfo(customerCode,
                Pageable.ofSize(10));
        Assert.assertEquals(consequentialTransaction.getTotalElements(), 1);
        Assert.assertEquals(checkOutRequest.getCartCode(), checkOutResult.getCart().getCode());
    }

    @Test
    @SneakyThrows
    public void createCheckoutTest_confirm_payment_channel_is_bank_based_on_transaction(){
        // arrange
        var checkOutRequest = new CheckOutRequest(EPaymentChannel.BANK_TRANSFER, cart.getCode(), "Testing checkout");
        var customerCode = customer.getCode();

        // act
        checkoutService.createCheckout(customerCode, checkOutRequest);

        // assert
        var consequentialTransaction = transactionRepo.findByCustomerInfo(customerCode,
                Pageable.ofSize(10));
        Assert.assertNotNull(consequentialTransaction.getTotalElements());
        var transaction = consequentialTransaction.stream().findFirst().get();
        Assert.assertEquals(transaction.getPaymentChannel(), EPaymentChannel.BANK_TRANSFER);
    }

    @Test
    @SneakyThrows
    public void createCheckoutTest_confirm_payment_channel_is_ussd_based_on_transaction(){
        // arrange
        var checkOutRequest = new CheckOutRequest(EPaymentChannel.USSD, cart.getCode(), "Testing checkout");
        var customerCode = customer.getCode();

        // act
        checkoutService.createCheckout(customerCode, checkOutRequest);

        // assert
        var consequentialTransaction = transactionRepo.findByCustomerInfo(customerCode,
                Pageable.ofSize(10));
        Assert.assertNotNull(consequentialTransaction.getTotalElements());
        var transaction = consequentialTransaction.stream().findFirst().get();
        Assert.assertEquals(transaction.getPaymentChannel(), EPaymentChannel.USSD);
    }

    private Author createAuthor(){
        Author author =  new Author();
        author.setFirstName("John");
        author.setLastName("Abayomi");
        author.setOtherInfo("Romance novel writer");
        authorRepo.save(author);
        return  author;
    }

    protected Customer customer = null;
    protected Book book = null;
    protected BookProduct bookProduct1;
    protected BookProduct bookProduct2;
}
