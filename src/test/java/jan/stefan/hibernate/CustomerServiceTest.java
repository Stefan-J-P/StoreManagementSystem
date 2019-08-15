package jan.stefan.hibernate;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerRepository;
import jan.stefan.hibernate.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest
{
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Add Or Update test with null argument")
    public void addOrUpdateTest1()
    {
        // GIVEN
        doThrow(new MyException("CUSTOMER SERVICE: addOrUpdate() : Customer object argument is null"))
                .when(customerRepository).saveOrUpdate(ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> customerService.addOrUpdate(null));

        assertTrue(throwable != null, "CUSTOMER SERVICE: addOrUpdate() : Customer object argument is null");
        //assertEquals("CUSTOMER SERVICE: addOrUpdate() : Customer object argument is null", throwable.getMessage());
    }

    /*
    @Test
    @DisplayName("Add Or Upadate test with non null argument")
    public void addOrUpdateNonNullArg()
    {
        // GIVEN
        CustomerDto c1 = CustomerDto.builder()
                .id(1L)
                .name("JAN")
                .surname("NOWAK")
                .email("jnowak@wp.pl")
                .age(44)
                .countryDto(CountryDto.builder().name("POLSKA").build())
                .build()

        //when();



    }   */

    /*
    @Test
    @DisplayName("Find All Customers size test")
    public void findAllCustomersTest()
    {
        when(customerRepository.findAll())


    }       */
















}
