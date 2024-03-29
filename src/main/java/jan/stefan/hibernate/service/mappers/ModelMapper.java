package jan.stefan.hibernate.service.mappers;

import jan.stefan.hibernate.dto.modelDto.*;
import jan.stefan.hibernate.model.*;
import jan.stefan.hibernate.model.MyError;

import java.util.HashSet;

public interface ModelMapper
{
    // COUNTRY -----------------------------------------------
    static CountryDto fromCountryToCountryDto(Country country) {
        return country == null ? null : CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    static Country fromCountryDtoToCountry(CountryDto countryDto) {
        return countryDto == null ? null : Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .producers(new HashSet<>())
                .customers(new HashSet<>())
                .shops(new HashSet<>())
                .build();
    }

    // CUSTOMER -----------------------------------------------
    static CustomerDto fromCustomerToCustomerDto(Customer customer) {
        return customer == null ? null : CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .surname(customer.getSurname())
                .countryDto(customer.getCountry() == null ? null : fromCountryToCountryDto(customer.getCountry()))
                .build();
    }

    static Customer fromCustomerDtoToCustomer(CustomerDto customerDto)
    {
        return customerDto == null ? null : Customer.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .age(customerDto.getAge())
                .email(customerDto.getEmail())
                .surname(customerDto.getSurname())
                .country(customerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(customerDto.getCountryDto()))
                .build();
    }

    // SHOP ---------------------------------------------------
    static ShopDto fromShopToShopDto(Shop shop)
    {
        return shop == null ? null : ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDto(shop.getCountry() == null ? null : fromCountryToCountryDto(shop.getCountry()))
                .build();
    }

    static Shop fromShopDtoToShop(ShopDto shopDto)
    {
        return shopDto == null ? null : Shop.builder()
                .id(shopDto.getId())
                .name(shopDto.getName())
                .country(shopDto.getCountryDto() == null ? null : fromCountryDtoToCountry(shopDto.getCountryDto()))
                .stocks(new HashSet<>())
                .build();
    }

    // TRADE -------------------------------------------------
    static TradeDto fromTradeToTradeDto(Trade trade)
    {
        return trade == null ? null : TradeDto.builder()
                .id(trade.getId())
                .name(trade.getTradeName())
                .build();
    }

    static Trade fromTradeDtoToTrade(TradeDto tradeDto)
    {
        return tradeDto == null ? null : Trade.builder()
                .id(tradeDto.getId())
                .tradeName(tradeDto.getName())
                .producers(new HashSet<>())
                .build();
    }

    // PRODUCER -----------------------------------------------
    static ProducerDto fromProducerToProducerDto(Producer producer)
    {
        return producer == null ? null : ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .countryDto(producer.getCountry() == null ? null : fromCountryToCountryDto(producer.getCountry()))
                .tradeDto(producer.getTrade() == null ? null : fromTradeToTradeDto(producer.getTrade()))
                .build();
    }

    static Producer fromProducerDtoToProducer(ProducerDto producerDto)
    {
        return producerDto == null ? null : Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .country(producerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(producerDto.getCountryDto()))
                .trade(producerDto.getTradeDto() == null ? null : fromTradeDtoToTrade(producerDto.getTradeDto()))
                .build();
    }

    // CATEGORY -----------------------------------------------
    static CategoryDto fromCategoryToCategoryDto(Category category)
    {
        return category == null ? null : CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    static Category fromCategoryDtoToCategory(CategoryDto categoryDto)
    {
        return categoryDto == null ? null : Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .products(new HashSet<>())
                .build();
    }


    // PRODUCT ------------------------------------------------
    static ProductDto fromProductToProductDto(Product product)
    {
        return product == null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryDto(product.getCategory() == null ? null : fromCategoryToCategoryDto(product.getCategory()))
                .producerDto(product.getProducer() == null ? null : fromProducerToProducerDto(product.getProducer()))
                .tradeDto(product.getTrade() == null ? null : fromTradeToTradeDto(product.getTrade()))
                .eGuarantees(product.getGuaranteeComponents())
                .build();
    }

    static Product fromProductDtoToProduct(ProductDto productDto)
    {
        return productDto == null ? null : Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .category(productDto.getCategoryDto() == null ? null : fromCategoryDtoToCategory(productDto.getCategoryDto()))
                .producer(productDto.getProducerDto() == null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                .trade(productDto.getTradeDto() == null ? null : fromTradeDtoToTrade(productDto.getTradeDto()))
                .guaranteeComponents(productDto.getEGuarantees())
                .myOrders(new HashSet<>())
                .stocks(new HashSet<>())
                .build();
    }

    static ProductDto fromNewProductDtoToProductDto(ProductDto ProductDto)
    {
        return ProductDto == null ? null : ProductDto.builder()
                .id(ProductDto.getId())
                .name(ProductDto.getName())
                .price(ProductDto.getPrice())
                .categoryDto(ProductDto.getCategoryDto())
                .producerDto(ProductDto.getProducerDto())
                .tradeDto(ProductDto.getTradeDto())
                .eGuarantees(ProductDto.getEGuarantees())
                .build();
    }

    static Product fromNewProductDtoToProduct(ProductDto ProductDto)
    {
        return ProductDto == null ? null : Product.builder()
                .id(ProductDto.getId())
                .name(ProductDto.getName())
                .price(ProductDto.getPrice())
                .category(ProductDto.getCategoryDto() == null ? null : fromCategoryDtoToCategory(ProductDto.getCategoryDto()))
                .producer(ProductDto.getProducerDto() == null ? null : fromProducerDtoToProducer(ProductDto.getProducerDto()))
                .trade(ProductDto.getTradeDto() == null ? null : fromTradeDtoToTrade(ProductDto.getTradeDto()))
                .guaranteeComponents(ProductDto.getEGuarantees())
                .build();
    }



    // STOCK ----------------------------------------------------
    static StockDto fromStockToStockDto(Stock stock)
    {
        return stock == null ? null : StockDto.builder()
                .id(stock.getId())
                .productDto(stock.getProduct() == null ? null : fromProductToProductDto(stock.getProduct()))
                .quantity(stock.getQuantity())
                .shopDto(stock.getShop() == null ? null : fromShopToShopDto(stock.getShop()))
                .build();
    }

    static Stock fromStockDtoToStock(StockDto stockDto)
    {
        return stockDto == null ? null : Stock.builder()
                .id(stockDto.getId())
                .product(stockDto.getProductDto() == null ? null : fromProductDtoToProduct(stockDto.getProductDto()))
                .quantity(stockDto.getQuantity())
                .shop(stockDto.getShopDto() == null ? null : fromShopDtoToShop(stockDto.getShopDto()))
                .build();
    }

    // PAYMENT ----------------------------------------------------
    static PaymentDto fromPaymenToPaymentDto(Payment payment)
    {
        return payment == null ? null : PaymentDto.builder()
                .id(payment.getId())
                .ePayment(payment.getPayment())
                .build();
    }

    static Payment fromPaymentDtoToPayment(PaymentDto paymentDto)
    {
        return paymentDto == null ? null : Payment.builder()
                .id(paymentDto.getId())
                .payment(paymentDto.getEPayment())
                .myOrders(new HashSet<>())
                .build();
    }

    // CUSTOMER ORDER --------------------------------------------
    static MyOrderDto fromCustomerOrderToCustomerOrderDto(MyOrder myOrder)
    {
        return myOrder == null ? null : MyOrderDto.builder()
                .id(myOrder.getId())
                .dateTime(myOrder.getDateTime())
                .discount(myOrder.getDiscount())
                .quantity(myOrder.getQuantity())
                .build();
    }

    static MyOrder fromCustomerOrderDtoToCustomerOrder(MyOrderDto myOrderDto)
    {
        return myOrderDto == null ? null : MyOrder.builder()
                .id(myOrderDto.getId())
                .dateTime(myOrderDto.getDateTime())
                .discount(myOrderDto.getDiscount())
                .quantity(myOrderDto.getQuantity())
                .customer(myOrderDto.getCustomerDto() == null ? null : fromCustomerDtoToCustomer(myOrderDto.getCustomerDto()))
                .product(myOrderDto.getProductDto() == null ? null : fromProductDtoToProduct(myOrderDto.getProductDto()))
                .payment(myOrderDto.getPaymentDto() == null ? null : fromPaymentDtoToPayment(myOrderDto.getPaymentDto()))
                .build();
    }

    // ERROR ------------------------------------------------------
    static MyErrorDto fromMyErrorToMyErrorDto(MyError myError)
    {
        return myError == null ? null : MyErrorDto.builder()
                .id(myError.getId())
                .dateTime(myError.getDateTime())
                .message(myError.getMessage())
                .build();
    }

    static MyError fromMyErrorDtoToMyError(MyErrorDto myErrorDto)
    {
        return myErrorDto == null ? null : MyError.builder()
                .id(myErrorDto.getId())
                .dateTime(myErrorDto.getDateTime())
                .message(myErrorDto.getMessage())
                .build();
    }






}

