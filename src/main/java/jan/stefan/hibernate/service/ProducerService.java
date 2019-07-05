package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.MyErrorDto;
import jan.stefan.hibernate.dto.modelDto.ProducerDto;
import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.model.validation.ProducerValidation;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProducerRepository;
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
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;
    private final ProducerValidation producerValidation;

    public ProducerDto addOrUpdate(ProducerDto producerDto)
    {
        if (producerDto == null)
        {
            throw new MyException("PRODUCER SERVICE: addOrUpdate() producerDto is null");
        }

        Producer producer = ModelMapper.fromProducerDtoToProducer(producerDto);
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
        return producerRepository.findById(id)
                .map(ModelMapper::fromProducerToProducerDto)
                .orElseThrow(() -> new MyException("PRODUCER SERVICE: addOrUpdate() cannot find id" + id));
    }


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
    }












}
