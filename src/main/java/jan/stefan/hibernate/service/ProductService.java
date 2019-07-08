package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.dto.newObjectDto.NewProductDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.model.validation.ProductValidation;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProductRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;
    private final ProductValidation productValidation;


    public ProductDto addOrUpdate(ProductDto productDto)
    {
        if (productDto == null)
        {
            throw new MyException("PRODUCT SERVICE: ProductDto object argument is null");
        }

        Product product = ModelMapper.fromProductDtoToProduct(productDto);
        return ModelMapper.fromProductToProductDto(productRepository
                .saveOrUpdate(product)
                .orElseThrow(()-> new MyException("CUSTOMER SERVICE: addOrUpdate() : Cannot addOrUpdate customer")
                ));
    }

    public List<ProductDto> findAll()
    {
        return productRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromProductToProductDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        productRepository.delete(id);
    }


    public ProductDto findOneById(Long id)
    {
        return productRepository
                .findById(id)
                .map(ModelMapper::fromProductToProductDto)
                .orElseThrow(() -> new MyException("PRODUCT SERVICE: findOneById() : Cannot find id: " + id));
    }


    public ProductDto addNewProduct(NewProductDto newProductDto)
    {
        // zrób osobną klasę DTO do przechowania danych pobranych od USERA i przekazać jako argument do metody addNewProduct() ze względu na testy jednostkowe
        /*
        ProductDto productDto = new ProductDto();
        productDto.setName(scannerService.getString("Enter name of the product:"));
        productDto.setPrice(scannerService.getBigDecimal("Enter value of the price:"));
        productDto.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter name of the category:")).build());
        productDto.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter name of the producer:")).build());
        productDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter name of the trade:")).build());
        productDto.setEGuarantee(scannerService.getEGuarantee());       */

        // lista wartości EGuarantee i wybór ich indeksów, które pobiorę do SET
        // pobierz  nazwy kraju, producenta etc poprzez repozytorium każdego z nich, potem ustaw setterami wartości dla obiektu

        ProductDto productDto = ModelMapper.fromNewProductDtoToProductDto(newProductDto);

        Map<String, String> errorsProduct = productValidation.validate(productDto);
        if (!productValidation.hasErrors())
        {
            addOrUpdate(productDto);
        }
        else
        {
            errorsProduct.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Product into table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        return productDto;
    }



}
