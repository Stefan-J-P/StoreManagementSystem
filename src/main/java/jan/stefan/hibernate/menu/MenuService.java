package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.model.validation.*;
import jan.stefan.hibernate.service.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MenuService
{
    // MenuService klasa która wykorzystuje metody serwisowe
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;

    private final CustomerValidation customerValidation;
    private final ShopValidation shopValidation;
    private final ProducerValidation producerValidation;
    private final ProductValidation productValidation;
    private final StockValidation stockValidation;
    private final OrderValidation orderValidation;
    private final CategoryValidation categoryValidation;
    private final CountryValidation countryValidation;
    private final TradeValidation tradeValidation;


    private final CustomerService customerService;
    private final ShopService shopService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final StockService stockService;
    private final MyOrderService myOrderService;
    private final CategoryService categoryService;
    private final CountryService countryService;
    private final TradeService tradeService;


    // ======================================= SERVICE METHODS =========================================


    //========================================================================================================================================================
    // CUSTOMER METHODS ----------------------------------------
    @SuppressWarnings("Duplicates")
    void customerOption0()
    {
        String myEmail = scannerService.getString("Enter the customer's email: ");
        CustomerDto myCustomerDto = customerService.findOneByEmail(myEmail);
        System.out.println("-----------------------------------------------------");
        System.out.println(myCustomerDto);
        System.out.println("-----------------------------------------------------");

        myCustomerDto.setName(scannerService.getString("Enter customer's NEW name:"));
        myCustomerDto.setSurname(scannerService.getString("Enter customer's NEW surname:"));
        myCustomerDto.setEmail(scannerService.getString("Enter customer's NEW email:"));
        myCustomerDto.setAge(scannerService.getInt("Enter customer's NEW age:"));
        myCustomerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of customer's NEW country")).build());

        Map<String, String> customerErrors = customerValidation.validate(myCustomerDto);

        if (customerValidation.hasErrors())
        {
            customerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while updating Customer in the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        customerService.addOrUpdate(myCustomerDto);
        System.out.println("Your customer has been updated");
    }


    @SuppressWarnings("Duplicates")
    void customerOption1()
    {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(scannerService.getString("Enter customer's name:"));
        customerDto.setSurname(scannerService.getString("Enter customer's surname:"));
        customerDto.setEmail(scannerService.getString("Enter customer's email:"));
        customerDto.setAge(scannerService.getInt("Enter customer's age:"));
        customerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of customer's country")).build());

        Map<String, String> customerErrors = customerValidation.validate(customerDto);

        if (customerValidation.hasErrors())
        {
            customerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Customer into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }

        customerService.addOrUpdate(customerDto);
        System.out.println("Your new customer has been added to the data base");
    }

    void customerOption2()
    {
        List<CustomerDto> customerDtos = customerService.findAll();
        if (customerDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        customerDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + customerDtos.size() + " CUSTOMERS");
    }

    void customerOption3()
    {
        Long customerId = scannerService.getLong("Enter customer's ID: ");
        CustomerDto customerDto = customerService.findOneById(customerId);
        if (customerDto == null)
        {
            System.out.println("CUSTOMER IS NULL");
            return;
        }
        System.out.println(customerDto);
    }

    void customerOption4()
    {
        String email = scannerService.getString("Enter the customer's email: ");
        CustomerDto customerDto = customerService.findOneByEmail(email);
        if (customerDto == null)
        {
            System.out.println("CUSTOMER IS NULL");
            return;
        }
        System.out.println(customerDto);
    }

    void customerOption5()
    {
        Long id = scannerService.getLong("Enter the customer's ID: ");
        customerService.delete(id);
    }

    //========================================================================================================================================================
    // SHOP METHODS -------------------------------------------
    @SuppressWarnings("Duplicates")
    void shopOption0()
    {
        String name = scannerService.getString("Enter the name of the shop: ");
        ShopDto myShopDto = shopService.findOneByName(name);
        System.out.println("-----------------------------------------------------");
        System.out.println(myShopDto);
        System.out.println("-----------------------------------------------------");

        myShopDto.setName(scannerService.getString("Enter the NEW name of the Shop: "));
        myShopDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the NEW name of the Country: ")).build());

        Map<String, String> shopErrors = shopValidation.validate(myShopDto);

        if (shopValidation.hasErrors())
        {
            shopErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while updating Shop in the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        shopService.addOrUpdate(myShopDto);
        System.out.println("Your Shop has been updated");
    }

    @SuppressWarnings("Duplicates")
    void shopOption1()
    {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(scannerService.getString("Enter the name of the Shop: "));
        shopDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the shop's country: ")).build());

        Map<String, String> shopErrors = shopValidation.validate(shopDto);

        if (shopValidation.hasErrors())
        {
            shopErrors.forEach((k, v) -> System.out.println(k + " " +v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Shop into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        shopService.addOrUpdate(shopDto);
        System.out.println("Your new Shop has been added");
    }
    void shopOption2()
    {
        List<ShopDto> shopDtos = shopService.findAll();
        if (shopDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY!");
            return;
        }
        shopDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + shopDtos.size() + " SHOPS");

    }
    void shopOption3()
    {
        Long shopId = scannerService.getLong("Enter the shop ID: ");
        ShopDto shopDto = shopService.findOneById(shopId);
        if (shopDto == null)
        {
            System.out.println("SHOP IS NULL");
            return;
        }
        System.out.println(shopDto);
    }

    void shopOption4()
    {
        String shopName = scannerService.getString("Enter the name of the shop");
        ShopDto shopDto = shopService.findOneByName(shopName);
        if (shopDto == null)
        {
            System.out.println("SHOP IS NULL");
            return;
        }
        System.out.println(shopDto);
    }

    void shopOption5()
    {
        Long delId = scannerService.getLong("Enter the shop ID: ");
        shopService.delete(delId);
    }

    //========================================================================================================================================================
    // PRODUCER METHODS ---------------------------------------
    @SuppressWarnings("Duplicates")
    void producerOption0()
    {
        String myName = scannerService.getString("Enter the name of the Producer: ");
        ProducerDto myProducerDto = producerService.findOneByName(myName);
        System.out.println("-----------------------------------------------------");
        System.out.println(myProducerDto);
        System.out.println("-----------------------------------------------------");

        myProducerDto.setName(scannerService.getString("Enter the NEW name of the Producer: "));
        myProducerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the NEW name of the  Country: ")).build());
        myProducerDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the NEW name of the Trade: ")).build());

        Map<String, String> producerErrors = producerValidation.validate(myProducerDto);

        if (producerValidation.hasErrors())
        {
            producerErrors.forEach((k, v) -> System.out.println(k + " " +v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while updating Producer in the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        producerService.addOrUpdate(myProducerDto);

    }

    @SuppressWarnings("Duplicates")
    void producerOption1()
    {
        ProducerDto producerDto = new ProducerDto();
        producerDto.setName(scannerService.getString("Enter the name of the producer: "));
        producerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the producer's country")).build());
        producerDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the producer's trade")).build());

        Map<String, String> producerErrors = producerValidation.validate(producerDto);

        if (producerValidation.hasErrors())
        {
            producerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Producer into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        producerService.addOrUpdate(producerDto);
    }

    void producerOption2()
    {
        List<ProducerDto> producerDtos = producerService.findAll();
        if (producerDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY!");
            return;
        }
        producerDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + producerDtos.size() + " PRODUCERS");
    }

    void producerOption3()
    {
        Long producerId = scannerService.getLong("Enter the ID of the producer: ");
        ProducerDto producerDto = producerService.findOneById(producerId);
        System.out.println(producerDto);
    }

    void producerOption4()
    {
        String producerName = scannerService.getString("Enter the name of the producer: ");
        ProducerDto producerDto = producerService.findOneByName(producerName);
        System.out.println(producerDto);
    }

    void producerOption5()
    {
        Long prodDelId = scannerService.getLong("Enter the ID of the producer to delete: ");
        producerService.delete(prodDelId);
    }


    //========================================================================================================================================================
    // PRODUCT METHODS ----------------------------------------
    void productOption0()
    {
        String name = scannerService.getString("Enter the name of the Product: ");
        ProductDto myProductDto = productService.findOneByName(name);
        System.out.println("-----------------------------------------------------");
        System.out.println(myProductDto);
        System.out.println("-----------------------------------------------------");

        myProductDto.setName(scannerService.getString("Enter the NEW name for the Product: "));
        myProductDto.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter the NEW name for the Category: ")).build());
        myProductDto.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter the NEW name for the Producer")).build());
        myProductDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the NEW name for the Trade: ")).build());
        myProductDto.setPrice(scannerService.getBigDecimal("Enter the NEW Price: "));
        //myProductDto.setEGuarantees(...);

        Map<String, String> productErrors = productValidation.validate(myProductDto);

        if (productValidation.hasErrors())
        {
            productErrors.forEach((k, v) -> System.out.println(k + " " +v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Product into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        productService.addOrUpdate(myProductDto);
    }

    @SuppressWarnings("Duplicates")
    void productOption1()
    {
        ProductDto productDto = new ProductDto();

        productDto.setName(scannerService.getString("Enter the name of the Prouct: "));
        productDto.setPrice(scannerService.getBigDecimal("Enter the price: "));
        productDto.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter the name of the Category: ")).build());
        productDto.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter the name of the Producer: ")).build());
        productDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the Category")).build());
        productDto.setEGuarantees(scannerService.getGuarantees("Enter values by comma , :"));

        Map<String, String> productErrors = productValidation.validate(productDto);

        if (!productValidation.hasErrors())
        {
            productService.addOrUpdate(productDto);
        }
        else
        {
            productErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Product into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
    }


    void productOption2()
    {
        List<ProductDto> productDtos = productService.findAll();
        /*
        if (productDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
            return;
        }
        productDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + productDtos.size() + " PRODUCTS");  */
    }

    void productOption3() // FIND ONE BY ID
    {
        Long productId = scannerService.getLong("Enter the ID of the Product: ");
        ProductDto productDto = productService.findOneById(productId);
        /*
        if (productDto != null)
        {
            System.out.println("!!!!!!!!!!");
        }   */
        System.out.println(productDto);
    }

    void productOption4()
    {
        String name = scannerService.getString("Enter the name of the Product: ");
        //ProductDto productDto = productService.

    }

    void productOption5()
    {
        Long prodDelId = scannerService.getLong("Enter the ID of the Product: ");
        productService.delete(prodDelId);

    }

    //========================================================================================================================================================
    // STOCK --------------------------------------------------

    void stockOption0()
    {
        String name = scannerService.getString("Enter the name of the Stock: ");

        // WPROWADŹ DODATKOWY UNIKALNY PARAMETR NP NAME

    }

    @SuppressWarnings("Duplicates")
    void stockOption1()
    {
        StockDto stockDto = new StockDto();
        stockDto.setProductDto(ProductDto.builder().name(scannerService.getString("Enter the NEW name of the Product: ")).build());
        stockDto.setQuantity(scannerService.getInt("Enter the NEW quantity: "));
        stockDto.setShopDto(ShopDto.builder().name(scannerService.getString("Enter the NEW name of the Shop: ")).build());

        Map<String, String> stockErrors = stockValidation.validate(stockDto);

        if (stockValidation.hasErrors())
        {
            stockErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        stockService.addOrUpdate(stockDto);
    }

    void stockOption2()
    {
        List<StockDto> stocks = stockService.findAll();
        if (stocks.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        stocks.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + stocks.size() + " STOCKS");
    }

    void stockOption3()
    {
        Long productId = scannerService.getLong("Enter the ID of the Product: ");
        StockDto stockDto = stockService.findOneById(productId);
        System.out.println("------------------ FOUNDED STOCK --------------------");
        System.out.println(stockDto);
        System.out.println("-----------------------------------------------------");
    }

    void stockOption4()
    {
        Long stockDto = scannerService.getLong("Enter the ID of the Product: ");
        stockService.delete(stockDto);
    }


    //========================================================================================================================================================
    // CUSTOMER ORDER -----------------------------------------
    @SuppressWarnings("Duplicates")
    void orderOption0()
    {
/*        Long orderNumber = scannerService.getLong("Enter the order number: ");
        OrderDto myOrder = orderService.findOneByNumber(orderNumber);
        System.out.println(myOrder);*/
    }

    @SuppressWarnings("Duplicates")
    void orderOption1()
    {
        MyOrderDto myOrderDto = new MyOrderDto();
        myOrderDto.setCustomerDto(CustomerDto.builder()
                .name(scannerService.getString("Enter the customer's name: "))
                .surname(scannerService.getString("Enter the customer's surname: "))
                .email(scannerService.getString("Enter the customers Email: "))
                .countryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the customer's Country: ")).build())
                .build());
        myOrderDto.setDateTime(LocalDateTime.now());
        myOrderDto.setDiscount(scannerService.getBigDecimal("Enter the value of the discount: "));
        myOrderDto.setProductDto(ProductDto.builder()
                .name(scannerService.getString("Enter the name of the Product: "))
                .categoryDto(CategoryDto.builder().name(scannerService.getString("Enter the name of the Category: ")).build())
                .build());
        myOrderDto.setQuantity(scannerService.getInt("Enter the number of the products: "));
        //orderDto.setPaymentDto(PaymentDto.builder().ePayment(scannerService.getEpayment()).build());


        Map<String, String> orderErrors = orderValidation.validate(myOrderDto);

        if (orderValidation.hasErrors())
        {
            orderErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        myOrderService.addOrUpdate(myOrderDto);

    }

    void orderOption2()
    {
        List<MyOrderDto> myOrderDtos = myOrderService.findAll();
        if (myOrderDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        myOrderDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + myOrderDtos.size() + " ORDERS");
    }

    void orderOption3()
    {
        Long id = scannerService.getLong("Enter the Order ID: ");
        MyOrderDto myOrderDto = myOrderService.findOneById(id);
        if (myOrderDto == null)
        {
            System.out.println("YOUR ORDER IS NULL");
            return;
        }
        System.out.println(myOrderDto);
    }
    void orderOption4()
    {
        Long id = scannerService.getLong("Enter the Customer's ID: ");
        myOrderService.delete(id);
    }

    void orderOption99()
    {
        Integer orderNumber = scannerService.getInt("Enter order Number: ");
        MyOrderDto myOrderDto = myOrderService.findOneByNumber(orderNumber);
        System.out.println(myOrderDto);
    }

    void orderOption77()
    {
        Integer res = myOrderService.generateOrderNumber();

        System.out.println("ORDER NUMBER = " + res);
    }


    //========================================================================================================================================================
    // CATEGORY -----------------------------------------------
    @SuppressWarnings("Duplicates")
    void categoryOption0()
    {
        String name = scannerService.getString("Enter name of the Category: ");
        CategoryDto categoryDto = categoryService.findOneByName(name);
        System.out.println(categoryDto);

        categoryDto.setName(scannerService.getString("Enter the NEW name of the Category: "));
        Map<String, String> categoryErrors = categoryValidation.validate(categoryDto);

        if (orderValidation.hasErrors())
        {
            categoryErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        categoryService.addOrUpdate(categoryDto);
    }

    @SuppressWarnings("Duplicates")
    void categoryOption1()
    {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(scannerService.getString("Enter the name of the Category: "));

        Map<String, String> categoryErrors = categoryValidation.validate(categoryDto);

        if (orderValidation.hasErrors())
        {
            categoryErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        categoryService.addOrUpdate(categoryDto);
    }

    void categoryOption2()
    {
        List<CategoryDto> categoryDtos = categoryService.findAll();
        if (categoryDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        categoryDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + categoryDtos.size() + " CATEGORIES");
    }

    void categoryOption3()
    {
        Long id = scannerService.getLong("Enter the ID of the Category: ");
        CategoryDto categoryDto = categoryService.findOneById(id);
        if (categoryDto == null)
        {
            System.out.println("YOUR CATEGORY IS NULL");
        }
        System.out.println(categoryDto);
    }

    void categoryOption4()
    {
        String name = scannerService.getString("Enter the name of the Category: ");
        CategoryDto categoryDto = categoryService.findOneByName(name);
        if (categoryDto == null)
        {
            System.out.println("YOUR CATEGORY IS NULL");
        }
        System.out.println(categoryDto);
    }

    void categoryOption5()
    {
        Long id = scannerService.getLong("Enter the name of the Country: ");
        countryService.delete(id);
    }

    //========================================================================================================================================================
    // COUNTRY ------------------------------------------------
    @SuppressWarnings("Duplicates")
    void countryOption0()
    {
        String name = scannerService.getString("Enter the name of the Country: ");
        CountryDto countryDto = countryService.findOneByName(name);
        countryDto.setName(scannerService.getString("Enter the NEW name of the Country: "));

        Map<String, String> countryErrors = countryValidation.validate(countryDto);

        if (countryValidation.hasErrors())
        {
            countryErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        countryService.addOrUpdate(countryDto);

    }

    @SuppressWarnings("Duplicates")
    void countryOption1()
    {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(scannerService.getString("Enter the name of the Country: "));

        Map<String, String> countryErrors = countryValidation.validate(countryDto);

        if (countryValidation.hasErrors())
        {
            countryErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        countryService.addOrUpdate(countryDto);

    }

    void countryOption2()
    {
        List<CountryDto> countryDtos = countryService.findAll();
        if (!countryDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS NULL");
        }
        countryDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + countryDtos.size() + " COUNTRIES");
    }

    void countryOption3()
    {
        Long id = scannerService.getLong("Enter the Country ID: ");
        CountryDto countryDto = countryService.findOneById(id);
        if (countryDto == null)
        {
            System.out.println("YOUR COUNTRY IS NULL");
        }
        System.out.println(countryDto);
    }

    void countryOption4()
    {
        String name = scannerService.getString("Enter the name of the Country: ");
        CountryDto countryDto = countryService.findOneByName(name);
        if (countryDto == null)
        {
            System.out.println("YOUR COUNTRY IS NULL");
        }
        System.out.println(countryDto);
    }

    void countryOption5()
    {
        Long id = scannerService.getLong("Enter the ID of the Country: ");
        countryService.delete(id);
    }

    //========================================================================================================================================================
    // TRADE --------------------------------------------------
    @SuppressWarnings("Duplicates")
    void tradeOption0()
    {
        String name = scannerService.getString("Enter the name of the Trade: ");
        TradeDto tradeDto = tradeService.findOneByName(name);

        tradeDto.setName(scannerService.getString("Enter the NEW name of the Trade: "));
        Map<String, String> tradeErrors = tradeValidation.validate(tradeDto);

        if (tradeValidation.hasErrors())
        {
            tradeErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        tradeService.addOrUpdate(tradeDto);
    }

    @SuppressWarnings("Duplicates")
    void tradeOption1()
    {
        TradeDto tradeDto = new TradeDto();
        tradeDto.setName(scannerService.getString("Enter the name of the Trade: "));

        Map<String, String> tradeErrors = tradeValidation.validate(tradeDto);

        if (tradeValidation.hasErrors())
        {
            tradeErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Stock into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        tradeService.addOrUpdate(tradeDto);
    }

    void tradeOption2()
    {
        List<TradeDto> tradeDtos = tradeService.findAll();
        if (tradeDtos.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        tradeDtos.forEach(System.out::println);
        System.out.println("YOU HAVE RIGHT NOW: " + tradeDtos.size() + " TRADES");
    }

    void tradeOption3()
    {
        Long id = scannerService.getLong("Enter the Trade ID: ");
        TradeDto tradeDto = tradeService.findOneById(id);
        if (tradeDto == null)
        {
            System.out.println("TRADE IS NULL");
            return;
        }
        System.out.println(tradeDto);
    }

    void tradeOption4()
    {
        String name = scannerService.getString("Enter the name of the Trade: ");
        TradeDto tradeDto = tradeService.findOneByName(name);
        if (tradeDto == null )
        {
            System.out.println("TRADE IS NULL");
            return;
        }
        System.out.println(tradeDto);
    }

    void tradeOption5()
    {
        Long id = scannerService.getLong("Enter the ID of the Trade: ");
        tradeService.delete(id);
    }



    /*static <T> String toJson( T t )
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(t);
    }*/


}
