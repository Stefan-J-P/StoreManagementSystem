package jan.stefan.hibernate;

import jan.stefan.hibernate.dto.modelDto.CategoryDto;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.ProducerDto;
import jan.stefan.hibernate.dto.modelDto.ProductDto;
import jan.stefan.hibernate.menu.MenuStatistics;
import jan.stefan.hibernate.service.CountryService;
import jan.stefan.hibernate.service.CustomerService;
import jan.stefan.hibernate.service.MyOrderService;
import jan.stefan.hibernate.service.ProductService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MyOrderTest
{
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

        Mockito.when(productService.findAll()).thenReturn(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9));

        // WHEN
        Map<CategoryDto, ProductDto> received = menuStatistics.mostExpensiveProductFromEachCategory();

        // THEN
        Map<CategoryDto, ProductDto> expected = new LinkedHashMap<>();
        expected.put(c1, p3); // Category = HOME, Product Dishwasher 1000,-
        expected.put(c2, p6); // Category = TOOLS, Product = Drill 700,-
        expected.put(c3, p9); // Category = ELECTRONICS, Product = Laptop 13 000,-

        assertEquals(received, expected, "TEST OK");
    }



}
