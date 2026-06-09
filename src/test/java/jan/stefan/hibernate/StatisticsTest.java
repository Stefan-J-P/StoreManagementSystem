package jan.stefan.hibernate;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.menu.MenuStatistics;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.service.CountryService;
import jan.stefan.hibernate.service.CustomerService;
import jan.stefan.hibernate.service.MyOrderService;
import jan.stefan.hibernate.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StatisticsTest
{

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private MyOrderService myOrderService;

    @Mock
    private CountryService countryService;

    @Mock
    private CustomerService customerService;

    @InjectMocks // obiekt do którego wstrzykuje Mock
    private MenuStatistics menuStatistics;

    @Test
    public void test0() {
        System.out.println("TEST");
    }




    @Test
    @DisplayName("Most expensive product from each category")
    public void test1()
    {
        // GIVEN
        ProductDto p1 = ProductDto.builder().name("DISHWASHER-1000").price(new BigDecimal(1000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("CHINA").build()).build()).categoryDto(CategoryDto.builder().name("HOME").build()).build();
        ProductDto p2 = ProductDto.builder().name("DISHWASHER-2000").price(new BigDecimal(2000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("GERMANY").build()).build()).categoryDto(CategoryDto.builder().name("HOME").build()).build();
        ProductDto p3 = ProductDto.builder().name("DISHWASHER-3000").price(new BigDecimal(3000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("USA").build()).build()).categoryDto(CategoryDto.builder().name("HOME").build()).build();

        ProductDto p4 = ProductDto.builder().name("DRILL-300").price(new BigDecimal(300)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("CHINA").build()).build()).categoryDto(CategoryDto.builder().name("TOOLS").build()).build();
        ProductDto p5 = ProductDto.builder().name("DRILL-500").price(new BigDecimal(500)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("GERMANY").build()).build()).categoryDto(CategoryDto.builder().name("TOOLS").build()).build();
        ProductDto p6 = ProductDto.builder().name("DRILL-700").price(new BigDecimal(700)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("USA").build()).build()).categoryDto(CategoryDto.builder().name("TOOLS").build()).build();

        ProductDto p7 = ProductDto.builder().name("LAPTOP-11000").price(new BigDecimal(11000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("CHINA").build()).build()).categoryDto(CategoryDto.builder().name("ELECTRONICS").build()).build();
        ProductDto p8 = ProductDto.builder().name("LAPTOP-12000").price(new BigDecimal(12000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("GERMANY").build()).build()).categoryDto(CategoryDto.builder().name("ELECTRONICS").build()).build();
        ProductDto p9 = ProductDto.builder().name("LAPTOP-13000").price(new BigDecimal(13000)).producerDto(ProducerDto.builder().name("BOSCH").countryDto(CountryDto.builder().name("USA").build()).build()).categoryDto(CategoryDto.builder().name("ELECTRONICS").build()).build();

        CategoryDto c1 = CategoryDto.builder().name("HOME").build();
        CategoryDto c2 = CategoryDto.builder().name("TOOLS").build();
        CategoryDto c3 = CategoryDto.builder().name("ELECTRONICS").build();

        when(productService.findAll()).thenReturn(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));

        // WHEN
        Map<CategoryDto, ProductDto> received = menuStatistics.mostExpensiveProductFromEachCategory();

        // THEN
        Map<CategoryDto, ProductDto> expected = new LinkedHashMap<>();
        expected.put(c1, p3); // Category = HOME, Product Dishwasher 1000,-
        expected.put(c2, p6); // Category = TOOLS, Product = Drill 700,-
        expected.put(c3, p9); // Category = ELECTRONICS, Product = Laptop 13 000,-

        assertEquals(received, expected, "TEST OK");
    }



    /*  DateTime                    Disc    ord.N.  Q   CustID  PayID   CountryID
    1,  2020-01-15 00:00:00.000000, 0.85,   1000,   1,  2,      1,      1
    2,  2020-02-15 00:00:00.000000, 0.85,   1001,   1,  4,      2,      1
    3,  2020-03-15 00:00:00.000000, 0.85,   1002,   1,  6,      3,      1
    4,  2020-04-15 00:00:00.000000, 0.85,   1003,   1,  8,      4,      7
    5,  2020-05-15 00:00:00.000000, 0.85,   1004,   1,  10,     5,      7
    6,  2020-06-15 00:00:00.000000, 0.85,   1005,   1,  11,     6,      7
    7,  2020-07-15 00:00:00.000000, 0.85,   1006,   1,  13,     1,      7
    8,  2020-08-15 00:00:00.000000, 0.85,   1007,   1,  14,     2,      15
    9,  2020-09-15 00:00:00.000000, 0.85,   1008,   1,  16,     3,      15
    10, 2020-10-15 00:00:00.000000, 0.85,   1009,   1,  17,     4,      15
    11, 2020-11-15 00:00:00.000000, 0.85,   1011,   1,  19,     5,      15
    12, 2020-12-15 00:00:00.000000, 0.85,   1012,   1,  20,     6,      15
     */
    @Test
    @DisplayName("Products By Country")
    public void test2()
    {

        CountryDto country = CountryDto.builder().name("USA").build();
        int ageFrom = 23;
        int ageTo = 40;

        MyOrderDto m1 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("A").age(20).countryDto(CountryDto.builder().name("USA").build()).build()).productDto(ProductDto.builder().name("1").build()).build();

        MyOrderDto m2 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("B").age(25).countryDto(CountryDto.builder().name("USA").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m3 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("C").age(30).countryDto(CountryDto.builder().name("USA").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m4 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("D").age(35).countryDto(CountryDto.builder().name("USA").build()).build()).productDto(ProductDto.builder().name("1").build()).build();

        MyOrder mm2 = MyOrder.builder().customer(Customer.builder().name("B").age(25).country(Country.builder().name("USA").build()).build()).product(Product.builder().name("1").build()).build();
        MyOrder mm3 = MyOrder.builder().customer(Customer.builder().name("C").age(30).country(Country.builder().name("USA").build()).build()).product(Product.builder().name("1").build()).build();
        MyOrder mm4 = MyOrder.builder().customer(Customer.builder().name("D").age(35).country(Country.builder().name("USA").build()).build()).product(Product.builder().name("1").build()).build();


        MyOrderDto m5 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("E").age(40).countryDto(CountryDto.builder().name("GERMANY").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m6 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("F").age(45).countryDto(CountryDto.builder().name("GERMANY").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m7 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("G").age(50).countryDto(CountryDto.builder().name("GERMANY").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m8 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("H").age(55).countryDto(CountryDto.builder().name("GERMANY").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m9 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("I").age(60).countryDto(CountryDto.builder().name("CZECH").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m10 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("J").age(70).countryDto(CountryDto.builder().name("CZECH").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m11 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("K").age(75).countryDto(CountryDto.builder().name("CZECH").build()).build()).productDto(ProductDto.builder().name("1").build()).build();
        MyOrderDto m12 = MyOrderDto.builder().customerDto(CustomerDto.builder().name("L").age(80).countryDto(CountryDto.builder().name("CZECH").build()).build()).productDto(ProductDto.builder().name("1").build()).build();



        Mockito
                .when(orderRepository.getProductsByCustomersCountry(country, ageFrom, ageTo)).thenReturn(List.of(mm2, mm3, mm4));



    }

    /*
    2,  65, eddie.kowalski@wp.pl,       EDDIE, KOWALSKI,     1
    4,  22, tracy.oberhauser@wp.pl,     TRACY, OBERHAUSER,   3
    6,  56, tracy.ramirez@wp.pl,        TRACY, RAMIREZ,      5
    8,  42, joey.lorenzo@wp.pl,         JOEY, LORENZO,       7
    10, 39, annie.kowalski@wp.pl,       ANNIE, KOWALSKI,     9
    11, 43, ross.lewis@wp.pl,           ROSS, LEWIS,         5
    13, 49, julia.kebekus@wp.pl,        JULIA, KEBEKUS,      12
    14, 33, michael.ramirez@wp.pl,      MICHAEL, RAMIREZ,    1
    16, 65, vladimir.macdonald@wp.pl,   VLADIMIR, MACDONALD, 15
    17, 25, stephen.kebekus@wp.pl,      STEPHEN, KEBEKUS,    12
    19, 36, thomas.rossi@wp.pl,         THOMAS, ROSSI,       18
    20, 38, michael.petrovich@wp.pl,    MICHAEL, PETROVICH,  18

     */













}






































