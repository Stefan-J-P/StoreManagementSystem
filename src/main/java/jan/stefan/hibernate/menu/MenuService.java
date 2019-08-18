package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.MyError;
import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.model.Product;
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
    // CUSTOMER METHODS ----------------------------------------
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

        if (!customerValidation.hasErrors())
        {
            customerService.addOrUpdate(customerDto);
        }
        else
        {
            customerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Customer into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
    }

    @SuppressWarnings("Duplicates")
    void customerOption0()
    {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(scannerService.getLong("Enter customer's ID: "));
        customerDto.setName(scannerService.getString("Enter customer's name:"));
        customerDto.setSurname(scannerService.getString("Enter customer's surname:"));
        customerDto.setEmail(scannerService.getString("Enter customer's email:"));
        customerDto.setAge(scannerService.getInt("Enter customer's age:"));
        customerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of customer's country")).build());

        Map<String, String> customerErrors = customerValidation.validate(customerDto);

        if (!customerValidation.hasErrors())
        {
            customerService.addOrUpdate(customerDto);
        }
        else
        {
            customerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Customer into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
    }

    void customerOption2()
    {
        List<CustomerDto> customerDtoList = customerService.findAll();
        //customerDtoList.isEmpty() ? System.out.println("YOUR LIST IS EMPTY") : customerDtoList.forEach(System.out::println);
        if (!customerDtoList.isEmpty())
        {
            customerDtoList.forEach(System.out::println);
        }
        else
        {
            System.out.println("YOUR LIST IS EMPTY");
        }
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

    // SHOP METHODS -------------------------------------------
    void shopOption1()
    {
        ShopDto shopDto = new ShopDto();
        shopDto.setName(scannerService.getString("Enter the name of the Shop: "));
        shopDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the shop's country: ")).build());

        Map<String, String> shopErrors = shopValidation.validate(shopDto);

        if (!shopValidation.hasErrors())
        {
            shopService.addOrUpdate(shopDto);
        }
        else
        {
            shopErrors.forEach((k, v) -> System.out.println(k + " " +v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Shop into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
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

    // PRODUCER METHODS ---------------------------------------
    void producerOption1()
    {
        ProducerDto producerDto = new ProducerDto();
        producerDto.setName(scannerService.getString("Enter the name of the producer: "));
        producerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the producer's country")).build());
        producerDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the producer's trade")).build());

        Map<String, String> producerErrors = producerValidation.validate(producerDto);

        if (!producerValidation.hasErrors())
        {
            producerService.addOrUpdate(producerDto);
        }
        else
        {
            producerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Producer into the table ")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
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



    // PRODUCT METHODS ----------------------------------------
    void productOption1()
    {
        ProductDto productDto = new ProductDto();
        productDto.setName(scannerService.getString("Enter the name of the Prouct: "));
        productDto.setPrice(scannerService.getBigDecimal("Enter the price: "));
        productDto.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter the name of the Category: ")).build());
        productDto.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter the name of the Producer: ")).build());
        productDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the Category")).build());
        //productDto.setEGuarantees();

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

    // STOCK --------------------------------------------------
    void stockOption1(){}
    void stockOption2(){}
    void stockOption3(){}
    void stockOption4(){}
    void stockOption5(){}

    // CUSTOMER ORDER -----------------------------------------
    void orderOption1(){}
    void orderOption2(){}
    void orderOption3(){}
    void orderOption4(){}
    void orderOption5(){}

    // CATEGORY -----------------------------------------------
    void categoryOption1(){}
    void categoryOption2(){}
    void categoryOption3(){}
    void categoryOption4(){}
    void categoryOption5(){}

    // COUNTRY ------------------------------------------------
    void countryOption1(){}
    void countryOption2(){}
    void countryOption3(){}
    void countryOption4(){}
    void countryOption5(){}

    // TRADE --------------------------------------------------
    void tradeOption1(){}
    void tradeOption2(){}
    void tradeOption3(){}
    void tradeOption4(){}
    void tradeOption5(){}



}
