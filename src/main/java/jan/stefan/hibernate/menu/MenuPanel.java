package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.service.ScannerService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuPanel
{
    private final ScannerService scannerService;
    private final MenuService menuService;
    private final MenuStatistics menuStatistics;

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
                System.out.println("6. CUSTOMER ORDER");
                System.out.println("7. CATEGORY");
                System.out.println("8. COUNTRY");
                System.out.println("9. TRADE");

                int optionMenu = scannerService.getInt("Enter the option");

                switch (optionMenu)
                {
                    case 1:
                        System.out.println("============ CUSTOMER ============");
                        System.out.println("1. ADD OR UPDATE ONE CUSTOMER");
                        System.out.println("2. FIND ALL CUSTOMERS");
                        System.out.println("3. FIND ONE CUSTOMER BY ID");
                        System.out.println("4. FIND ONE CUSTOMER BY NAME");
                        System.out.println("5. DELETE ONE CUSTOMER");
                        int customerOption = scannerService.getInt("Enter the option for the Customer: ");

                        switch (customerOption)
                        {
                            case 1: // add or update one customer
                                menuService.customerOption1();
                            case 2: // find all customers
                                menuService.customerOption2();
                            case 3: // find one customer by id
                                menuService.customerOption3();
                            case 4: // find one customer by name
                                menuService.customerOption4();
                            case 5: // delete one customer
                                menuService.customerOption5();
                        }
                    case 2:
                        System.out.println("============= SHOP ==============");
                        System.out.println("1. ADD OR UPDATE ONE SHOP");
                        System.out.println("2. FIND ALL SHOPS");
                        System.out.println("3. FIND ONE SHOP BY ID");
                        System.out.println("4. FIND ONE SHOP BY NAME");
                        System.out.println("5. DELETE ONE SHOP");
                        int shopOption = scannerService.getInt("Enter the option for the Shop: ");
                        switch (shopOption)
                        {
                            case 1:
                                menuService.shopOption1();
                            case 2:
                                menuService.shopOption2();
                            case 3:
                                menuService.shopOption3();
                            case 4:
                                menuService.shopOption4();
                            case 5:
                                menuService.shopOption5();
                        }
                    case 3:
                        System.out.println("============ PRODUCER =============");
                        System.out.println("1. ADD OR UPDATE ONE PRODUCER");
                        System.out.println("2. FIND ALL PRODUCERS");
                        System.out.println("1. FIND ONE PRODUCER BY ID");
                        System.out.println("1. FIND ONE PRODUCER BY NAME");
                        System.out.println("1. DELETE ONE PRODUCER");
                        int optionProducer = scannerService.getInt("Enter the option for the Producer: ");
                        switch (optionProducer)
                        {
                            case 1:
                                menuService.producerOption1();
                            case 2:
                                menuService.producerOption2();
                            case 3:
                                menuService.producerOption3();
                            case 4:
                                menuService.producerOption4();
                            case 5:
                                menuService.producerOption5();
                        }
                    case 4:
                        System.out.println("=================== PRODUCT ====================");
                        System.out.println("1. ADD OR UPDATE ONE PRODUCT");
                        System.out.println("2. FIND ALL PRODUCTS");
                        System.out.println("3. FIND ONE PRODUCT BY ID");
                        System.out.println("3. FIND ONE PRODUCT BY NAME");
                        System.out.println("3. DELETE ONE PRODUCT");
                        int optionProduct = scannerService.getInt("Enter the option for the Product: ");
                        switch (optionProduct)
                        {
                            case 1:
                                menuService.productOption1();
                            case 2:
                                menuService.productOption2();
                            case 3:
                                menuService.productOption3();
                            case 4:
                                menuService.productOption4();
                            case 5:
                                menuService.productOption5();
                        }
                    case 5:
                        System.out.println("=================== STOCK =======================");
                        System.out.println("1. ADD OR UPDATE ONE STOCK");
                        System.out.println("2. FIND ALL STOCKS");
                        System.out.println("3. FIND ONE STOCK BY ID");
                        System.out.println("4. FIND ONE STOCK BY NAME");
                        System.out.println("5. DELETE ONE STOCK");
                        int optionStock = scannerService.getInt("Enter the option for the Stock: ");
                        switch (optionStock)
                        {
                            case 1:
                                menuService.stockOption1();
                            case 2:
                                menuService.stockOption2();
                            case 3:
                                menuService.stockOption3();
                            case 4:
                                menuService.stockOption4();
                            case 5:
                                menuService.stockOption5();
                        }
                    case 6:
                        System.out.println("======================== ORDER ==========================");
                        System.out.println("1. ADD OR UPDATE ONE ORDER");
                        System.out.println("2. FIND ALL ORDERS");
                        System.out.println("3. FIND ONE ORDER BY ID");
                        System.out.println("4. FIND ONE ORDER BY NAME");
                        System.out.println("5. DELETE ONE ORDER");
                        int optionOrder = scannerService.getInt("Enter the option for the Order: ");
                        switch (optionOrder)
                        {
                            case 1:
                                menuService.orderOption1();
                            case 2:
                                menuService.orderOption2();
                            case 3:
                                menuService.orderOption3();
                            case 4:
                                menuService.orderOption4();
                            case 5:
                                menuService.orderOption5();
                        }
                    case 7:
                        System.out.println("========================= CATEGORY ==========================");
                        System.out.println("1. ADD OR UPDATE ONE CATEGORY");
                        System.out.println("2. FIND ALL CATEGORIES");
                        System.out.println("3. FIND ONE CATEGORY BY ID");
                        System.out.println("4. FIND ONE CATEGORY BY NAME");
                        System.out.println("5. DELETE ONE CATEGORY");
                        int optionCategory = scannerService.getInt("Enter the option for the Category: ");
                        switch (optionCategory)
                        {
                            case 1:
                                menuService.categoryOption1();
                            case 2:
                                menuService.categoryOption2();
                            case 3:
                                menuService.categoryOption3();
                            case 4:
                                menuService.categoryOption4();
                            case 5:
                                menuService.categoryOption5();
                        }
                    case 8:
                        System.out.println("=================== COUNTRY =======================");
                        System.out.println("1. ADD OR UPDATE ONE COUNTRY");
                        System.out.println("2. FIND ALL COUNTRIES");
                        System.out.println("3. FIND ONE COUNTRY BY ID");
                        System.out.println("4. FIND ONE COUNTRY BY NAME");
                        System.out.println("5. DELETE ONE COUNTRY");
                        int optionCountry = scannerService.getInt("Enter the option for the Category: ");
                        switch (optionCountry)
                        {
                            case 1:
                                menuService.countryOption1();
                            case 2:
                                menuService.countryOption2();
                            case 3:
                                menuService.countryOption3();
                            case 4:
                                menuService.countryOption4();
                            case 5:
                                menuService.categoryOption5();
                        }
                    case 9:
                        System.out.println("======================== TRADE ========================");
                        System.out.println("1. ADD OR UPDATE ONE TRADE");
                        System.out.println("2. FIND ALL TRADES");
                        System.out.println("3. FIND ONE TRADE BY ID");
                        System.out.println("4. FIND ONE TRADE BY NAME");
                        System.out.println("5. DELETE ONE TRADE");
                        int optionTrade = scannerService.getInt("Enter the option for the Trade: ");
                        switch (optionTrade)
                        {
                            case 1:
                                menuService.tradeOption1();
                            case 2:
                                menuService.tradeOption2();
                            case 3:
                                menuService.tradeOption3();
                            case 4:
                                menuService.tradeOption4();
                            case 5:
                                menuService.tradeOption5();
                        }
                }
            } catch (MyException e)
            {
                System.out.println("*************************** EXCEPTION ********************************************");
                System.err.println(e.getMessage());
                System.out.println("**********************************************************************************");
            }
        }
    }
}
