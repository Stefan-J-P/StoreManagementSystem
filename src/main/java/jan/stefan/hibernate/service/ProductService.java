package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.dto.newObjectDto.NewProductDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.model.Trade;
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
    private final DataBaseValidator dataBaseValidator;


    public ProductDto addOrUpdate(ProductDto productDto)
    {
        if (productDto == null)
        {
            throw new MyException("PRODUCT SERVICE: ProductDto object argument is null");
        }

        Product product = ModelMapper.fromProductDtoToProduct(productDto);
        Category category = dataBaseValidator.categoryDbValidator(productDto.getCategoryDto());
        Producer producer = dataBaseValidator.producerDbValidator(productDto.getProducerDto());
        Trade trade = dataBaseValidator.tradeDbValidator(productDto.getTradeDto());

        product.setCategory(category);
        product.setProducer(producer);
        product.setTrade(trade);

        return ModelMapper.fromProductToProductDto(productRepository
                .saveOrUpdate(product)
                .orElseThrow(()-> new MyException("PRODUCT SERVICE: addOrUpdate() : Cannot addOrUpdate customer")
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





}
