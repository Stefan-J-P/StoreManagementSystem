package jan.stefan.hibernate;

import jan.stefan.hibernate.exceptions.MyException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest
{
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Save Or Update test with null argument")
    public void saveOrUpdateTest1()
    {
        // GIVEN
        doThrow(new MyException("CUSTOMER SERVICE: Customer object argument is null"))
                .when(customerRepository).saveOrUpdate(ArgumentMatchers.isNull());

        // WHEN + THEN
        MyException throwable = assertThrows(MyException.class, () -> customerService.addOrUpdate(null));

        assertTrue(throwable != null, "Exception object is null");
        assertEquals("CUSTOMER SERVICE: Customer object argument is null", throwable.getMessage());
    }











}
