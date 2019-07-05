package jan.stefan.hibernate.dto.newObjectDto;

import jan.stefan.hibernate.dto.modelDto.CategoryDto;
import jan.stefan.hibernate.dto.modelDto.ProducerDto;
import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.model.enums.EGuarantee;
import jan.stefan.hibernate.service.ScannerService;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class NewProductDto
{
    private String name;
    private BigDecimal price;
    private CategoryDto categoryDto;
    private ProducerDto producerDto;
    private TradeDto tradeDto;
    private Set<EGuarantee> eGuarantees;

    private ScannerService scannerService = new ScannerService();

    /**
     * This method creates new object which will transfer data to ProductDto class
     * @return
     */
    public NewProductDto createNewProductDto()
    {

        NewProductDto npd = NewProductDto.builder()
                .name(scannerService.getString("Enter name of the product: "))
                .price(scannerService.getBigDecimal("Enter the value of the price:"))
                .categoryDto(CategoryDto.builder().name(scannerService.getString("Enter the name of the category: ")).build())
                .producerDto(ProducerDto.builder().name(scannerService.getString("Enter the name of the producer: ")).build())
                .tradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the trade: ")).build())
                .build();

        /*
        npd.setName(scannerService.getString("Enter name of the product: "));
        npd.setPrice(scannerService.getBigDecimal("Enter the value of the price:"));
        npd.setCategoryDto(CategoryDto.builder().name(scannerService.getString("Enter the name of the category: ")).build());
        npd.setProducerDto(ProducerDto.builder().name(scannerService.getString("Enter the name of the producer: ")).build());
        npd.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the trade: ")).build());

        int guaranteeNumber = scannerService.getInt("Enter number: how many services you want to add?");
        Set<EGuarantee> mySet = new HashSet<>();
        for (int i = 0; i < guaranteeNumber; ++i)
        {
            mySet.add(scannerService.getEGuarantee());
        }
        npd.setEGuarantees(mySet);  */

        return npd;
    }





}
