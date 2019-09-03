package jan.stefan.hibernate.dataInDbValidation;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.*;
import jan.stefan.hibernate.repository.repositoryInterfaces.*;
import jan.stefan.hibernate.service.CustomerService;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataBaseValidator
{
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;
    private final CustomerRepository customerRepository;
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final TradeRepository tradeRepository;
    //private final CustomerService customerService;

    @SuppressWarnings("Duplicates")
    public Category categoryDbValidator(CategoryDto categoryDto)
    {
        if (categoryDto == null)
        {
            throw new MyException("DATA BASE VALIDATION: categoryDbValidator// categoryDto object is null");
        }
        Category category = ModelMapper.fromCategoryDtoToCategory(categoryDto);
        Category categoryFromDb = null;

        if (category.getId() == null)
        {
            categoryFromDb = categoryRepository.findOneByName(category.getName()).orElse(null);

            if (categoryFromDb == null)
            {
                categoryFromDb = categoryRepository.saveOrUpdate(category).orElseThrow(() -> new MyException("DATA BASE VALIDATION: categoryDbValidator() // categoryFromDb EXCEPTION"));
                return categoryFromDb;
            }
            return categoryFromDb;
        }
        else
        {
            Category categoryWithId = categoryRepository.findById(category.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: categoryDbValidator// categoryWithId EXCEPTION"));
            return categoryWithId;
        }
    }


    @SuppressWarnings("Duplicates")
    public Country countryDbValidator(CountryDto countryDto)
    {
        if (countryDto == null)
        {
            throw new MyException("DATA BASE VALIDATION SERVICE: countryDbValidator() // countryDto object is null");
        }
        Country country = ModelMapper.fromCountryDtoToCountry(countryDto);
        Country countryFromDb = null;

        if (country.getId() == null)    // find country by its ID
        {   // if country doesn't have ID ---> find country in DB by its name
            countryFromDb = countryRepository.findOneByName(country.getName()).orElse(null);

            if (countryFromDb == null)
            {   // if country from db doesn't have name ---> add new country to data base
                countryFromDb = countryRepository.saveOrUpdate(country).orElseThrow(() -> new MyException("DATA BASE VALIDATION SERVICE: countryDbValidator() // countryFromDb EXCEPTION"));
                return countryFromDb;
            }
            return countryFromDb;
        }
        else
        {   // if country does have ID ---> return it
            Country countryWithId = countryRepository.findById(country.getId()).orElseThrow(() -> new MyException("SHOP SERVICE: countryDbValidator() // countryWithId EXCEPTION"));
            return countryWithId;
        }
    }

    @SuppressWarnings("Duplicates")
    public Customer customerDbValidator(CustomerDto customerDto)
    {
        if (customerDto == null)
        {
            throw new MyException("DATA BASE VALIDATOR// customerDto object is null");
        }
        Customer customer = ModelMapper.fromCustomerDtoToCustomer(customerDto);
        Customer customerFromDb = null;

        if (customer.getId() == null)
        {
            customerFromDb = customerRepository.findOneByEmail(customer.getEmail()).orElse(null);

            if (customerFromDb == null)
            {
                customerFromDb = customerRepository.saveOrUpdate(customer).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: customerDbValidation// customerFromDb EXCEPTION"));
                return customerFromDb;
            }
            throw new MyException("username with given email already exists");
        }
        else
        {
            Customer customerWithId = customerRepository.findById(customer.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: customerDbValidator// customerWithId EXCEPTION"));
            return customerWithId;
        }
    }

    @SuppressWarnings("Duplicates")
    public Producer producerDbValidator(ProducerDto producerDto)
    {
        if (producerDto == null)
        {
            throw new MyException("DATA BASE VALIDATOR// producerDto object is null");
        }
        Producer producer = ModelMapper.fromProducerDtoToProducer(producerDto);
        Producer producerFromDb = null;

        if (producer.getId() == null)
        {
            producerFromDb = producerRepository.findOneByName(producer.getName()).orElse(null);

            if (producerFromDb == null)
            {
                producerFromDb = producerRepository.saveOrUpdate(producer).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: producerDbValidation// producerFromDb EXCEPTION"));
                return producerFromDb;
            }
            return producerFromDb;
        }
        else
        {
            Producer producerWithId = producerRepository.findById(producer.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: producerDbValidator// producerWithId EXCEPTION"));
            return producerWithId;
        }
    }

    @SuppressWarnings("Duplicates")
    public Product productDbValidator(ProductDto productDto)
    {
        if (productDto == null)
        {
            throw new MyException("DATA BASE VALIDATOR// productDto object is null");
        }
        Product product = ModelMapper.fromProductDtoToProduct(productDto);
        Product productFromDb = null;

        if (product.getId() == null)
        {
            productFromDb = productRepository.findOneByName(product.getName()).orElse(null);

            if (productFromDb == null)
            {
                productFromDb = productRepository.saveOrUpdate(product).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: productDbValidation// productFromDb EXCEPTION"));
                return productFromDb;
            }
            return productFromDb;
        }
        else
        {
            Product productWithId = productRepository.findById(product.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: productDbValidator// productWithId EXCEPTION"));
            return productWithId;
        }
    }



    @SuppressWarnings("Duplicates")
    public Shop shopDbValidator(ShopDto shopDto)
    {
        if (shopDto == null)
        {
            throw new MyException("STOCK SERVICE: shopDbValidator() // shopDto object is null");
        }
        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        Shop shopFromDb = null;

        if (shop.getId() == null)
        {
            shopFromDb = shopRepository.findOneByName(shop.getName()).orElse(null);

            if (shopFromDb == null)
            {
                shopFromDb = shopRepository.saveOrUpdate(shop).orElseThrow(() -> new MyException("SHOP SERVICE: shopDbValidator() // countryFromDb EXCEPTION"));
                return shopFromDb;
            }
            return shopFromDb;
        }
        else
        {
            Shop shopWithId = shopRepository.findById(shop.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: shopDbValidator// countryFromDb EXCEPTION"));
            return shopWithId;
        }
    }

    @SuppressWarnings("Duplicates")
    public Trade tradeDbValidator(TradeDto tradeDto)
    {
        if (tradeDto == null)
        {
            throw new MyException("STOCK SERVICE: shopDbValidator() // shopDto object is null");
        }
        Trade trade = ModelMapper.fromTradeDtoToTrade(tradeDto);
        Trade tradeFromDb = null;

        if (trade.getId() == null)
        {
            tradeFromDb = tradeRepository.findOneByName(trade.getTradeName()).orElse(null);

            if (tradeFromDb == null)
            {
                tradeFromDb = tradeRepository.saveOrUpdate(trade).orElseThrow(() -> new MyException("SHOP SERVICE: shopDbValidator() // countryFromDb EXCEPTION"));
                return tradeFromDb;
            }
            return tradeFromDb;
        }
        else
        {
            Trade tradeWithId = tradeRepository.findById(trade.getId()).orElseThrow(() -> new MyException("DATA BASE VALIDATOR: shopDbValidator// countryFromDb EXCEPTION"));
            return tradeWithId;
        }
    }










}





















