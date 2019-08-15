package jan.stefan.hibernate;


import jan.stefan.hibernate.connection.DbConnection;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.ShopDto;
import jan.stefan.hibernate.dto.newObjectDto.NewProductDto;
import jan.stefan.hibernate.menu.MenuPanel;
import jan.stefan.hibernate.menu.MenuService;
import jan.stefan.hibernate.menu.MenuStatistics;
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

        var dataBaseValidation = new DataBaseValidator(categoryRepository, countryRepository, customerRepository, producerRepository, productRepository, shopRepository, tradeRepository);

        var categoryService = new CategoryService(categoryRepository);
        var countryService = new CountryService(countryRepository);
        var customerOrderService = new CustomerOrderService(customerOrderRepository, dataBaseValidation);

        var myErrorService = new MyErrorService(myErrorRepository);
        var paymentService = new PaymentService(paymentRepository);

        var stockService = new StockService(stockRepository, dataBaseValidation);
        var tradeService = new TradeService(tradeRepository);

        var customerService = new CustomerService(customerRepository, dataBaseValidation);
        var shopService = new ShopService(shopRepository, dataBaseValidation);
        var producerService = new ProducerService(producerRepository, dataBaseValidation);
        var productService = new ProductService(productRepository, dataBaseValidation);

        var menuStatistics = new MenuStatistics();
        var menuService = new MenuService(
                scannerService,
                myErrorService,
                customerValidation,
                shopValidation,
                producerValidation,
                productValidation,
                stockValidation,
                customerOrderValidation,
                categoryValidation,
                countryValidadtion,
                tradeValidation,
                customerService,
                shopService,
                producerService,
                productService,
                stockService,
                customerOrderService,
                categoryService,
                countryService,
                tradeService
        );

        // ====================================================================================================================================================


        new MenuPanel(
                scannerService,
                menuService,
                menuStatistics





        ).mainMenu();











        DbConnection.getInstance().close();

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

    }
}
