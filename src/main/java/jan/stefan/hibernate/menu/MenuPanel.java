package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.service.ScannerService;
import jan.stefan.hibernate.service.dataGenerator.DataManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MenuPanel
{
    private final ScannerService scannerService;
    private final MenuService menuService;
    private final MenuStatistics menuStatistics;
    private final DataManager dataManager;

    public void mainMenu()
    {
        while (true)
        {
            try
            {
                System.out.println("MENU");
                System.out.println("1. CUSTOMER");
                System.out.println("2. SHOP");
                System.out.println("3. PRODUCER");
                System.out.println("4. PRODUCT");
                System.out.println("5. STOCK");
                System.out.println("6. ORDER");
                System.out.println("7. CATEGORY");
                System.out.println("8. COUNTRY");
                System.out.println("9. TRADE");
                System.out.println("10. STATISTICS");
                System.out.println("11. DATA GENERATING");
                System.out.println("100. CLOSE PROGRAM");

                int optionMenu = scannerService.getInt("Enter the option");

                switch (optionMenu)
                {
                    case 1:
                        System.out.println("============ CUSTOMER ============");
                        System.out.println("0. UPDATE ONE CUSTOMER");
                        System.out.println("1. ADD ONE CUSTOMER");
                        System.out.println("2. FIND ALL CUSTOMERS");
                        System.out.println("3. FIND ONE CUSTOMER BY ID");
                        System.out.println("4. FIND ONE CUSTOMER BY EMAIL");
                        System.out.println("5. DELETE ONE CUSTOMER");
                        System.out.println("6. RETURN TO MAIN MENU");
                        int customerOption = scannerService.getInt("Enter the option for the Customer: ");

                        switch (customerOption)
                        {
                            case 0:
                                menuService.customerOption0();
                                break;
                            case 1: // add or update one customer
                                menuService.customerOption1();
                                break;
                            case 2: // find all customers
                                menuService.customerOption2();
                                break;
                            case 3: // find one customer by id
                                menuService.customerOption3();
                                break;
                            case 4: // find one customer by name
                                menuService.customerOption4();
                                break;
                            case 5: // delete one customer
                                menuService.customerOption5();
                                break;
                            case 6:
                                break;
                        }
                        break;

                    case 2:
                        System.out.println("============= SHOP ==============");
                        System.out.println("0. UPDATE ONE SHOP");
                        System.out.println("1. ADD ONE SHOP");
                        System.out.println("2. FIND ALL SHOPS");
                        System.out.println("3. FIND ONE SHOP BY ID");
                        System.out.println("4. FIND ONE SHOP BY NAME");
                        System.out.println("5. DELETE ONE SHOP");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int shopOption = scannerService.getInt("Enter the option for the Shop: ");

                        switch (shopOption)
                        {
                            case 0: // update one shop
                                menuService.shopOption0();
                                break;
                            case 1: // add one shop
                                menuService.shopOption1();
                                break;
                            case 2: // find all shops
                                menuService.shopOption2();
                                break;
                            case 3: // find one shop by id
                                menuService.shopOption3();
                                break;
                            case 4: // find one shop by name
                                menuService.shopOption4();
                                break;
                            case 5: // delete one shop
                                menuService.shopOption5();
                                break;
                            case 6: // return to main menu
                                break;
                        }
                        break;

                    case 3:
                        System.out.println("============ PRODUCER =============");
                        System.out.println("0. UPDATE ONE PRODUCER");
                        System.out.println("1. ADD ONE PRODUCER");
                        System.out.println("2. FIND ALL PRODUCERS");
                        System.out.println("3. FIND ONE PRODUCER BY ID");
                        System.out.println("4. FIND ONE PRODUCER BY NAME");
                        System.out.println("5. DELETE ONE PRODUCER");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int optionProducer = scannerService.getInt("Enter the option for the Producer: ");

                        switch (optionProducer)
                        {
                            case 0: // update one producer
                                menuService.producerOption0();
                                break;
                            case 1: // add one producer
                                menuService.producerOption1();
                                break;
                            case 2: // find all producers
                                menuService.producerOption2();
                                break;
                            case 3: // find one producer by ID
                                menuService.producerOption3();
                                break;
                            case 4: // find one producer by name
                                menuService.producerOption4();
                                break;
                            case 5: // delete one producer
                                menuService.producerOption5();
                                break;
                            case 6: // return to the main menu
                                break;
                        }
                        break;

                    case 4:
                        System.out.println("=================== PRODUCT ====================");
                        System.out.println("0. UPDATE ONE PRODUCT");
                        System.out.println("1. ADD ONE PRODUCT");
                        System.out.println("2. FIND ALL PRODUCTS");
                        System.out.println("3. FIND ONE PRODUCT BY ID");
                        System.out.println("4. FIND ONE PRODUCT BY NAME");
                        System.out.println("5. DELETE ONE PRODUCT");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int optionProduct = scannerService.getInt("Enter the option for the Product: ");

                        switch (optionProduct)
                        {
                            case 0:
                                menuService.productOption0();
                                break;
                            case 1:
                                menuService.productOption1();
                                break;
                            case 2:
                                menuService.productOption2();
                                break;
                            case 3:
                                menuService.productOption3();
                                break;
                            case 4:
                                menuService.productOption4();
                                break;
                            case 5:
                                menuService.productOption5();
                                break;
                            case 6:
                                break;
                        }
                        break;

                    case 5:
                        System.out.println("=================== STOCK =======================");
                        System.out.println("0. UPDATE ONE STOCK");
                        System.out.println("1. ADD ONE STOCK");
                        System.out.println("2. FIND ALL STOCKS");
                        System.out.println("3. FIND ONE STOCK BY ID");
                        System.out.println("4. DELETE ONE STOCK");
                        System.out.println("5. RETURN TO MAIN MENU");

                        int optionStock = scannerService.getInt("Enter the option for the Stock: ");

                        switch (optionStock)
                        {
                            case 0:
                                menuService.stockOption0();
                                break;
                            case 1:
                                menuService.stockOption1();
                                break;
                            case 2:
                                menuService.stockOption2();
                                break;
                            case 3:
                                menuService.stockOption3();
                                break;
                            case 4:
                                menuService.stockOption4();
                                break;
                            case 5:
                                break;
                        }
                        break;

                    case 6:
                        System.out.println("======================== ORDER ==========================");
                        System.out.println("0. UPDATE ONE ORDER");
                        System.out.println("1. ADD ONE ORDER");
                        System.out.println("2. FIND ALL ORDERS");
                        System.out.println("3. FIND ONE ORDER BY ID");
                        System.out.println("4. DELETE ONE ORDER");
                        System.out.println("5. RETURN TO MAIN MENU");
                        System.out.println("77. GENERATE ORDER NUMBER");
                        System.out.println("99. FIND ONE ORDER BY ORDER NUMBER");


                        int optionOrder = scannerService.getInt("Enter the option for the Order: ");

                        switch (optionOrder)
                        {
                            case 0:
                                menuService.orderOption0();
                                break;
                            case 1:
                                menuService.orderOption1();
                                break;
                            case 2:
                                menuService.orderOption2();
                                break;
                            case 3:
                                menuService.orderOption3();
                                break;
                            case 4:
                                menuService.orderOption4();
                                break;
                            case 5:
                                break;
                            case 99:
                                menuService.orderOption99();
                                break;
                            case 77:
                                menuService.orderOption77();
                                break;
                        }
                        break;

                    case 7:
                        System.out.println("========================= CATEGORY ==========================");
                        System.out.println("0. UPDATE ONE CATEGORY");
                        System.out.println("1. ADD ONE CATEGORY");
                        System.out.println("2. FIND ALL CATEGORIES");
                        System.out.println("3. FIND ONE CATEGORY BY ID");
                        System.out.println("4. FIND ONE CATEGORY BY NAME");
                        System.out.println("5. DELETE ONE CATEGORY");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int optionCategory = scannerService.getInt("Enter the option for the Category: ");

                        switch (optionCategory)
                        {
                            case 0:
                                menuService.categoryOption0();
                                break;
                            case 1:
                                menuService.categoryOption1();
                                break;
                            case 2:
                                menuService.categoryOption2();
                                break;
                            case 3:
                                menuService.categoryOption3();
                                break;
                            case 4:
                                menuService.categoryOption4();
                                break;
                            case 5:
                                menuService.categoryOption5();
                                break;
                            case 6:
                                break;
                        }
                        break;

                    case 8:
                        System.out.println("=================== COUNTRY =======================");
                        System.out.println("0. UPDATE ONE COUNTRY");
                        System.out.println("1. ADD ONE COUNTRY");
                        System.out.println("2. FIND ALL COUNTRIES");
                        System.out.println("3. FIND ONE COUNTRY BY ID");
                        System.out.println("4. FIND ONE COUNTRY BY NAME");
                        System.out.println("5. DELETE ONE COUNTRY");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int optionCountry = scannerService.getInt("Enter the option for the Category: ");

                        switch (optionCountry)
                        {
                            case 0:
                                menuService.countryOption0();
                                break;
                            case 1:
                                menuService.countryOption1();
                                break;
                            case 2:
                                menuService.countryOption2();
                                break;
                            case 3:
                                menuService.countryOption3();
                                break;
                            case 4:
                                menuService.countryOption4();
                                break;
                            case 5:
                                menuService.countryOption5();
                                break;
                            case 6:
                                break;
                        }
                        break;

                    case 9:
                        System.out.println("======================== TRADE ========================");
                        System.out.println("0. UPDATE ONE TRADE");
                        System.out.println("1. ADD ONE TRADE");
                        System.out.println("2. FIND ALL TRADES");
                        System.out.println("3. FIND ONE TRADE BY ID");
                        System.out.println("4. FIND ONE TRADE BY NAME");
                        System.out.println("5. DELETE ONE TRADE");
                        System.out.println("6. RETURN TO MAIN MENU");

                        int optionTrade = scannerService.getInt("Enter the option for the Trade: ");

                        switch (optionTrade)
                        {
                            case 0:
                                menuService.tradeOption0();
                                break;
                            case 1:
                                menuService.tradeOption1();
                                break;
                            case 2:
                                menuService.tradeOption2();
                                break;
                            case 3:
                                menuService.tradeOption3();
                                break;
                            case 4:
                                menuService.tradeOption4();
                                break;
                            case 5:
                                menuService.tradeOption5();
                                break;
                            case 6:
                                break;
                        }
                        break;

                    case 10:
                        System.out.println("======================== STATISTICS ========================");
                        System.out.println("0. MAP");


                        int optionStat = scannerService.getInt("Enter the option for the Trade: ");

                        switch (optionStat)
                        {
                            case 0:
                                menuStatistics.mostExpensiveProductFromEachCategory();
                                break;

                            case 1:
                                //menuStatistics.allProductsFromCountryWithAgeInRange();
                                menuStatistics.productsByCountry(CountryDto.builder().name("POLAND").build());
                                break;

                            case 6:
                                break;
                        }
                        break;

                    case 11:
                        System.out.println("======================== DATA GENERATOR ========================");
                        System.out.println("0. GENERATE");

                        int optionGenerate = scannerService.getInt("Enter the option for the generator: ");

                        switch (optionGenerate)
                        {
                            case 0:
                                List<String> names = dataManager.readFile("surnames.txt");
                                //names.forEach(System.out::println);
                                String str = dataManager.generateCustomerName(names);
                                System.out.println(str);


                                break;

                            case 99:
                                break;
                        }
                        break;


                    case 100: // CLOSE PROGRAM
                        System.out.println("CLOSING PROGRAM");
                        scannerService.close();
                        return;

                }
            } catch (MyException e)
            {
                e.printStackTrace();
                System.out.println("*************************** EXCEPTION ********************************************");
                System.err.println(e.getMessage());
                System.out.println("**********************************************************************************");
            }
        }
    }
}













