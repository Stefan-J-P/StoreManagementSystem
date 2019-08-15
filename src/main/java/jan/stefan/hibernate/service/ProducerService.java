package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.MyErrorDto;
import jan.stefan.hibernate.dto.modelDto.ProducerDto;
import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.model.Trade;
import jan.stefan.hibernate.model.validation.ProducerValidation;
import jan.stefan.hibernate.repository.repositoryInterfaces.CountryRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProducerRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.TradeRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProducerService
{
    private final ProducerRepository producerRepository;
    private final DataBaseValidator dataBaseValidator;

    public ProducerDto addOrUpdate(ProducerDto producerDto)
    {
        if (producerDto == null)
        {
            throw new MyException("PRODUCER SERVICE: addOrUpdate() producerDto is null");
        }

        Producer producer = ModelMapper.fromProducerDtoToProducer(producerDto);
        Country country = dataBaseValidator.countryDbValidator(producerDto.getCountryDto());
        Trade trade = dataBaseValidator.tradeDbValidator(producerDto.getTradeDto());
        producer.setCountry(country);
        producer.setTrade(trade);

        return ModelMapper
                .fromProducerToProducerDto(producerRepository
                .saveOrUpdate(producer)
                .orElseThrow(() -> new MyException("PRODUCER SERVICE: addOrUpdate() cannot addOrUpdate producer")));
    }

    public List<ProducerDto> findAll()
    {
        return producerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromProducerToProducerDto)
                .collect(Collectors.toList());
    }


    public void delete(Long id)
    {
        producerRepository.delete(id);
    }


    public ProducerDto findOneById(Long id)
    {
        return producerRepository
                .findById(id)
                .map(ModelMapper::fromProducerToProducerDto)
                .orElseThrow(() -> new MyException("PRODUCER SERVICE: findOneById() cannot find id" + id));
    }

    public ProducerDto findOneByName(String producerName)
    {
        return producerRepository
                .findOneByName(producerName)
                .map(ModelMapper::fromProducerToProducerDto)
                .orElseThrow(() -> new MyException("PRODUCER SERVICE: findOneByName() cannot find name " + producerName));
    }

    /*
    public ProducerDto addNewProducer()
    {
        ProducerDto producerDto = new ProducerDto();

        producerDto.setName(scannerService.getString("Enter the name of the Producer:"));
        producerDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the country:")).build());
        producerDto.setTradeDto(TradeDto.builder().name(scannerService.getString("Enter the name of the trade:")).build());

        Map<String, String> errorsProducer = producerValidation.validate(producerDto);

        if(!producerValidation.hasErrors())
        {
            addOrUpdate(producerDto);
        }
        else
        {
            errorsProducer.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Producer into table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }

        return producerDto;
    }   */














}
