package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.PaymentDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Payment;
import jan.stefan.hibernate.repository.repositoryInterfaces.PaymentRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PaymentService
{
    private final PaymentRepository paymentRepository;

    public PaymentDto addOrUpdate(PaymentDto paymentDto)
    {
        if (paymentDto == null)
        {
            throw new MyException("PAYMENT SERVICE: paymentDto is null");
        }

        Payment payment = ModelMapper.fromPaymentDtoToPayment(paymentDto);
        return ModelMapper.fromPaymenToPaymentDto(paymentRepository
                .saveOrUpdate(payment)
                .orElseThrow(() -> new MyException("PAYMENT SERVICE: cannot addOrUpdate() payment")));
    }

    public List<PaymentDto> findAll()
    {
        return paymentRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromPaymenToPaymentDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        paymentRepository.delete(id);
    }

    public PaymentDto findOneById(Long id)
    {
        return paymentRepository
                .findById(id)
                .map(ModelMapper::fromPaymenToPaymentDto)
                .orElseThrow(() -> new MyException("PAYMENT SERVICE: findOneById() cannot find id: " + id));
    }




}
