package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.MyErrorDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.MyError;
import jan.stefan.hibernate.repository.repositoryInterfaces.MyErrorRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MyErrorService
{
    private final MyErrorRepository myErrorRepository;

    public MyErrorDto addOrUpdateOneMyError(MyErrorDto myErrorDto)
    {
        if (myErrorDto == null)
        {
            throw new MyException("ERROR SERVICE: addOneError() exception" + myErrorDto.getMessage());
        }

        MyError myError = ModelMapper.fromMyErrorDtoToMyError(myErrorDto);
        return ModelMapper.fromMyErrorToMyErrorDto(myErrorRepository
                .saveOrUpdate(myError)
                .orElseThrow(() -> new MyException("MY ERROR SERVICE: cannot addOrUpdate() myError")));
    }

    public List<MyErrorDto> findAllMyErrors()
    {
        return myErrorRepository.
                findAll().
                stream().
                map(ModelMapper::fromMyErrorToMyErrorDto).
                collect(Collectors.toList());
    }

    public void deleteOneMyError(Long id)
    {
        myErrorRepository.delete(id);
    }

    public MyErrorDto findOneMyErrorById(Long id)
    {
        return myErrorRepository
                .findById(id)
                .map(ModelMapper::fromMyErrorToMyErrorDto)
                .orElseThrow(() -> new MyException("MY ERROR SERVICE: findOneById() Cannot find id: " + id));
    }


}
