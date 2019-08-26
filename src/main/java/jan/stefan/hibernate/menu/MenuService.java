package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.model.*;
import jan.stefan.hibernate.model.validation.*;
import jan.stefan.hibernate.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final CustomerOrderValidation customerOrderValidation;
    private final CategoryValidation categoryValidation;
    private final CountryValidation countryValidation;
    private final TradeValidation tradeValidation;


    private final CustomerService customerService;
    private final ShopService shopService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final StockService stockService;
    private final CustomerOrderService customerOrderService;
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

        myCustomerDto.setName(scannerService.getString("Enter customer's name:"));
        myCustomerDto.setSurname(scannerService.getString("Enter customer's surname:"));
        myCustomerDto.setEmail(scannerService.getString("Enter customer's email:"));
        myCustomerDto.setAge(scannerService.getInt("Enter customer's age:"));
        myCustomerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of customer's country")).build());

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
        var customerDtoList = customerService.findAll();
        if (customerDtoList.isEmpty())
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
        customerDtoList.forEach(System.out::println);

        // System.out.println(customerDtoList.isEmpty() ? customerDtoList : "ssss");

    }

    void customerOption3()
    {
        Long customerId = scannerService.getLong("Enter customer's ID: ");
        CustomerDto customerDto = customerService.findOneById(customerId);
        System.out.println(customerDto);
    }

    void customerOption4()
    {
        String email = scannerService.getString("Enter the customer's email: ");
        CustomerDto customerDto = customerService.findOneByEmail(email);
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

        myShopDto.setName(scannerService.getString("Enter name of the Shop: "));
        myShopDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the Country: ")).build());

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
        List<ShopDto> shopDtoList = shopService.findAll();
        if (!shopDtoList.isEmpty())
        {
            shopDtoList.forEach(System.out::println);
        }
        else
        {
            System.out.println("YOUR LIST IS EMPTY!");
        }
    }
    void shopOption3()
    {
        Long shopId = scannerService.getLong("Enter the shop ID: ");
        ShopDto shopDto = shopService.findOneById(shopId);
        System.out.println(shopDto);
    }

    void shopOption4()
    {
        String shopName = scannerService.getString("Enter the name of the shop");
        ShopDto myShop = shopService.findOneByName(shopName);
        System.out.println(myShop);
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

        myProducerDto.setName(scannerService.getString("Enter the new name of the Producer: "));
        myProducerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the new Country: ")).build());
        myProducerDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the new Trade: ")).build());

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
        List<ProducerDto> producerDtoList = producerService.findAll();
        if (!producerDtoList.isEmpty())
        {
            producerDtoList.forEach(System.out::println);
        }
        else
        {
            System.out.println("YOUR LIST IS EMPTY!");
        }

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

        myProductDto.setName(scannerService.getString("Enter the new name for the Product: "));
        myProductDto.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter the new name for the Category: ")).build());
        myProductDto.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter the new name for the Producer")).build());
        myProductDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the new name for the Trade: ")).build());
        myProductDto.setPrice(scannerService.getBigDecimal("Enter the new Price: "));
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
        //productDto.setEGuarantees(scannerService.getGuarantees("Enter values by comma , :"));

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
        List<ProductDto> productDtoList = productService.findAll();
        if (!productDtoList.isEmpty())
        {
            productDtoList.forEach(System.out::println);
        }
        else
        {
            System.out.println("YOUR LIST IS EMPTY");
        }

    }

    void productOption3()
    {
        Long productId = scannerService.getLong("Enter the ID of the Product: ");
        ProductDto productDto = productService.findOneById(productId);
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

    }

    void stockOption1()
    {
        StockDto stockDto = new StockDto();
        stockDto.setProductDto(ProductDto.builder().name(scannerService.getString("Enter the name of the Product: ")).build());
        stockDto.setQuantity(scannerService.getInt("Enter the quantity: "));
        stockDto.setShopDto(ShopDto.builder().name(scannerService.getString("Enter the name of the Shop: ")).build());

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

    }

    @SuppressWarnings("Duplicates")
    void orderOption1()
    {

    }

    void orderOption2(){}
    void orderOption3(){}
    void orderOption4(){}
    void orderOption5(){}


    //========================================================================================================================================================
    // CATEGORY -----------------------------------------------
    @SuppressWarnings("Duplicates")
    void categoryOption0()
    {

    }

    @SuppressWarnings("Duplicates")
    void categoryOption1()
    {
        System.out.println("-----------------------------------------------------");
    }

    void categoryOption2()
    {

    }

    void categoryOption3()
    {

    }

    void categoryOption4()
    {

    }

    void categoryOption5()
    {

    }

    //========================================================================================================================================================
    // COUNTRY ------------------------------------------------
    @SuppressWarnings("Duplicates")
    void countryOption0()
    {

    }

    @SuppressWarnings("Duplicates")
    void countryOption1()
    {
        System.out.println("-----------------------------------------------------");
    }

    void countryOption2()
    {

    }

    void countryOption3()
    {

    }

    void countryOption4()
    {

    }

    void countryOption5()
    {

    }

    //========================================================================================================================================================
    // TRADE --------------------------------------------------
    @SuppressWarnings("Duplicates")
    void tradeOption0()
    {

    }

    @SuppressWarnings("Duplicates")
    void tradeOption1()
    {
        System.out.println("-----------------------------------------------------");
    }

    void tradeOption2()
    {

    }

    void tradeOption3()
    {

    }

    void tradeOption4()
    {

    }

    void tradeOption5()
    {

    }


    /*static <T> String toJson( T t )
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(t);
    }*/


}
