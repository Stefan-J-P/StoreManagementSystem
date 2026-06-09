package jan.stefan.hibernate.menu;

import com.mysql.cj.protocol.x.Notice;
import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.implementation.OrderRepositoryImpl;
import jan.stefan.hibernate.service.CountryService;
import jan.stefan.hibernate.service.CustomerService;
import jan.stefan.hibernate.service.MyOrderService;
import jan.stefan.hibernate.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MenuStatistics
{
    //private final OrderRepositoryImpl orderRepositoryImpl;

    private final ProductService productService;
    private final MyOrderService myOrderService;
    private final CountryService countryService;
    private final CustomerService customerService;


    //a. Pobranie z bazy danych pełnej informacji na temat produktów o największej cenie w każdej kategorii.
    // Informacja zawiera nazwę produktu, cenę produktu, kategorię produktu, nazwę producenta,
    // kraj w którym wyprodukowano produkt oraz ilość pozycji, w którym zamawiano ten produkt.
    public Map<CategoryDto, ProductDto> mostExpensiveProductFromEachCategory()
    {
        Map<CategoryDto, List<ProductDto>> resMap = productService
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(ProductDto::getCategoryDto))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue(),
                        (v1, v2) -> v1, LinkedHashMap::new
                ));

        Map<CategoryDto, ProductDto> ultimateMap = resMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue()
                                .stream()
                                .max(Comparator.comparing(ProductDto::getPrice))
                                .orElseThrow(() -> new MyException("MENU STATISTICS: theMostExpensiveProductFromEachCategory"))
                ));

        System.out.println("=================== ULTIMATE MAP ===========================");
        ultimateMap.forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("==========================================================");
        return ultimateMap;

    }


    // b. Pobranie z bazy danych listy wszystkich produktów,
    // które zamawiane były przez klientów pochodzących z kraju o nazwie X
    // podanej przez użytkownika i wieku z przedziału określanego przez użytkownika.

    // MYORDER - CUSTOMER - PRODUCT

    // Produkty powinny być posortowane malejąco według ceny.
    // Informacja zawiera nazwę produktu, cenę produktu, kategorię produktu, nazwę producenta
    // oraz kraj w którym wyprodukowano produkt.

    public List<MyOrderDto> productsByCountry(CountryDto countryDto, int ageFrom, int ageTo)
    {
        List<MyOrderDto> myOrders1 = myOrderService.getProductsByCustomerAndCountry(countryDto, ageFrom, ageTo);

/*        myOrders1
                .stream()*/



        return null;
    }


    //c. Pobranie z bazy danych listy produktów,
    // które obejmuje gwarancja i które w ramach gwarancji mają zapewnione usługi
    // o nazwach podanych przez użytkownika. Pogrupuj te produkty według kategorii.



    // d. Pobranie z bazy danych listy sklepów, które w magazynie posiadają produkty,
    // których kraj pochodzenia jest inny niż kraje, w których występują oddziały sklepu.



    // e. Pobranie z bazy danych producentów o nazwie branży podanej przez użytkownika,
    // którzy wyprodukowali produkty o łącznej ilości sztuk większej niż liczba podana przez użytkownika.?!?!?




    // f. Pobranie z bazy danych zamówień, które złożono w przedziale dat pobranym od użytkownika
    // o kwocie zamówienia (po uwzględnieniu zniżki) większej niż wartość podana przez użytkownika.



    // g. Pobranie z bazy danych listy produktów, które zamówił klient
    // o imieniu, nazwisku oraz nazwie kraju pochodzenia pobranych od użytkownika.
    // Produkty należy pogrupować ze względu na producenta, który wyprodukował produkt.



    // h. Pobierz z bazy danych listę tych klientów, którzy zamówili przynajmniej jeden produkt
    // pochodzący z tego samego kraju co klient.
    // Informacje o kliencie powinny zawierać imię, nazwisko, wiek, nazwę kraju pochodzenia klienta
    // oraz ilość zamówionych produktów pochodzących z innego kraju niż kraj klienta.


    // ERRORS STATISTICS
    // a. Podaj nazwę tabeli, która najczęściej generowała błąd.
    // b. Podaj datę, dla której wystąpiło najwięcej błędów.
    // c. Podaj nazwę komunikatu błędu, który pojawiał się najczęściej w tabeli błędów.
}











