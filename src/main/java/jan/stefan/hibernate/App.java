package jan.stefan.hibernate;


import jan.stefan.hibernate.connection.DbConnection;

import jan.stefan.hibernate.dto.newObjectDto.NewProductDto;
import jan.stefan.hibernate.model.validation.*;
import jan.stefan.hibernate.repository.implementation.*;
import jan.stefan.hibernate.service.*;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

public class App
{
    public static void main(String[] args)
    {
        EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();
        var scannerService = new ScannerService();
        var customerValidation = new CustomerValidation();
        var countryValidadtion = new CountryValidation();
        var categoryValidation = new CategoryValidation();
        var shopValidation = new ShopValidation();
        var customerOrderValidation = new CustomerOrderValidation();
        var paymentValidation = new PaymentValidation();
        var producerValidation = new ProducerValidation();
        var productValidation = new ProductValidation();
        var stockValidation = new StockValidation();
        var tradeValidation = new TradeValidation();
        var categoryRepository = new CategoryRepositoryImpl();
        var countryRepository = new CountryRepositoryImpl();
        var customerOrderRepository = new CustomerOrderRepositoryImpl();

        var customerRepository = new CustomerRepositoryImpl();
        var myErrorRepository = new MyErrorRepositoryImpl();
        var paymentRepository = new PaymentRepositoryImpl();

        var producerRepository = new ProducerRepositoryImpl();
        var productRepository = new ProductRepositoryImpl();
        var shopRepository = new ShopRepositoryImpl();

        var stockRepository = new StockRepositoryImpl();
        var tradeRepository = new TradeRepositoryImpl();

        var categoryService = new CategoryService(categoryRepository);
        var countryService = new CountryService(countryRepository);
        var customerOrderService = new CustomerOrderService(customerOrderRepository);


        var myErrorService = new MyErrorService(myErrorRepository);
        var paymentService = new PaymentService(paymentRepository);




        var stockService = new StockService(stockRepository);
        var tradeService = new TradeService(tradeRepository);

        var customerService = new CustomerService(customerRepository, scannerService, myErrorService, customerValidation);
        var shopService = new ShopService(shopRepository, countryRepository, countryService, scannerService, myErrorService, shopValidation);
        var producerService = new ProducerService(producerRepository, scannerService, myErrorService, producerValidation);
        var productService = new ProductService(productRepository, scannerService, myErrorService, productValidation);

        // ====================================================================================================================================================

        //shopService.addNewShop();
        /*
        NewProductDto newProductDto = new NewProductDto();
        NewProductDto res =  newProductDto.createNewProductDto();
        System.out.println(res);
        productService.addNewProduct(res);  */











        DbConnection.getInstance().close();
        /*
        customerOrderService.addOrUpdate(CustomerOrderDto.builder()
                .quantity(3)
                .dateTime(LocalDateTime.now())
                .discount(new BigDecimal(0.5))
                .customerDto(CustomerDto.builder().id(4L).build())
                .productDto(null)
                .paymentDto(null)
                .build()
        );  */


                /*
        shopService.addOrUpdate(ShopDto.builder()
                .name("LEWIATAN")
                .countryDto(CountryDto.builder().build())
                .build());  */


        //System.out.println(customerService.findOneById(4L));
        //System.out.println(countryRepository.findOneByName("POLAND"));

                /*
        producerService.addOrUpdate(ProducerDto.builder()
                .name("PHILIPS")
                .countryDto(CountryDto.builder().id(1L).name("ANGLIA").build())
                .tradeDto(null)
                .build());  */


        /*
        CustomerDto cus1 = CustomerDto.builder()
                .name("Jurek")
                .surname("Nowak")
                .email("jnowak@gmail.com")
                .age(17)
                .build();

        CustomerValidation cv = new CustomerValidation();
        Map<String, String> errors = cv.validate(cus1);
        if (cv.hasErrors()) {
            // dodaj do db blad
        }
        else
        {
            customerService.addOrUpdate(cus1);
        }
        */

        /*
        try
        {
            Shop shop = Shop.builder().build();
            Class<? extends Shop> shopClass = shop.getClass();
            Field field = shopClass.getDeclaredField("id");
            field.setAccessible(true);
            System.out.println(field.get(shop));
        } catch (Exception e) {}
        */







        // NOWA POZYCJA W MAGAZYNIE



    }
}
