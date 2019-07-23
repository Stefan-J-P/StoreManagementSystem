package jan.stefan.hibernate.menu;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.dto.modelDto.MyErrorDto;
import jan.stefan.hibernate.model.MyError;
import jan.stefan.hibernate.model.validation.CustomerValidation;
import jan.stefan.hibernate.service.CustomerService;
import jan.stefan.hibernate.service.MyErrorService;
import jan.stefan.hibernate.service.ScannerService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@RequiredArgsConstructor
public class MenuService
{
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;

    private final CustomerValidation customerValidation;

    private final CustomerService customerService;


    // ======================================= SERVICE METHODS =========================================
    // CUSTOMER METHODS ----------------------------------------
    @SuppressWarnings("Duplicates")
    protected void customerOption1()
    {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(scannerService.getString("Enter customer's name:"));
        customerDto.setSurname(scannerService.getString("Enter customer's surname:"));
        customerDto.setEmail(scannerService.getString("Enter customer's email:"));
        customerDto.setAge(scannerService.getInt("Enter customer's age:"));

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

    protected void customerOption2()
    {

    }

    protected void customerOption3()
    {

    }

    protected void customerOption4()
    {

    }

    protected void customerOption5()
    {

    }

    // SHOP METHODS -------------------------------------------
    protected void shopOption1(){}
    protected void shopOption2(){}
    protected void shopOption3(){}
    protected void shopOption4(){}
    protected void shopOption5(){}

    // PRODUCER METHODS ---------------------------------------
    protected void producerOption1(){}
    protected void producerOption2(){}
    protected void producerOption3(){}
    protected void producerOption4(){}
    protected void producerOption5(){}

    // PRODUCT METHODS ----------------------------------------
    protected void productOption1(){}
    protected void productOption2(){}
    protected void productOption3(){}
    protected void productOption4(){}
    protected void productOption5(){}

    // STOCK --------------------------------------------------
    protected void stockOption1(){}
    protected void stockOption2(){}
    protected void stockOption3(){}
    protected void stockOption4(){}
    protected void stockOption5(){}

    // CUSTOMER ORDER -----------------------------------------
    protected void orderOption1(){}
    protected void orderOption2(){}
    protected void orderOption3(){}
    protected void orderOption4(){}
    protected void orderOption5(){}

    // CATEGORY -----------------------------------------------
    protected void categoryOption1(){}
    protected void categoryOption2(){}
    protected void categoryOption3(){}
    protected void categoryOption4(){}
    protected void categoryOption5(){}

    // COUNTRY ------------------------------------------------
    protected void countryOption1(){}
    protected void countryOption2(){}
    protected void countryOption3(){}
    protected void countryOption4(){}
    protected void countryOption5(){}

    // TRADE --------------------------------------------------
    protected void tradeOption1(){}
    protected void tradeOption2(){}
    protected void tradeOption3(){}
    protected void tradeOption4(){}
    protected void tradeOption5(){}



}
