package jan.stefan.hibernate.service.dataGenerator;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.service.CustomerService;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
@RequiredArgsConstructor
public class DataManager
{

    private final static Random rnd = new Random();
    private final CustomerDto customerDto;
    private final CustomerService customerService;
    private final DataBaseValidator dataBaseValidator;

    //private final ScannerService scannerService;

    public List<String> readFile(String filename)
    {
        List<String> namesFromList = new ArrayList<>();
        try
        {
            FileReader reader = new FileReader(filename);
            Scanner sc = new Scanner(reader);
            while (sc.hasNextLine())
            {
                namesFromList.add(sc.next());
            }
            sc.close();
            reader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return namesFromList;
    }

    public String generateRandomStringFromList(List<String> myList)
    {
        return myList.get(rnd.nextInt(myList.size()));
    }

    public BigDecimal generateRandomDiscount()
    {
        try
        {
            BigDecimal min = new BigDecimal(0.15);
            BigDecimal max = new BigDecimal(0.5);
            return min.add(new BigDecimal(Math.random()).multiply(max.subtract(min))).round(new MathContext(2, RoundingMode.HALF_UP));
        } catch (Exception e)
        {
            throw new MyException("GENERATE DISCOUNT EXCEPTION: " + e.getMessage());
        }
    }

    public BigDecimal generateRandomPrice()
    {
        try
        {
            BigDecimal min = new BigDecimal(100);
            BigDecimal max = new BigDecimal(10000);
            return min.add(new BigDecimal(Math.random()).multiply(max.subtract(min))).round(new MathContext(2, RoundingMode.HALF_UP));
        } catch (Exception e)
        {
            throw new MyException("GENERATE DISCOUNT EXCEPTION: " + e.getMessage());
        }
    }

    public Integer generateRandomAge()
    {
        Integer min = 18;
        Integer max = 70;
        return rnd.nextInt((max - min) + 1) + min;
    }

    public void emailTest(String str)
    {
        boolean test = dataBaseValidator.emailValidate(str);
        System.out.println("TEST: " + test);
    }

    public void nameTest(String str)
    {
        boolean test = dataBaseValidator.nameValidate(str);
        System.out.println("TEST: " + test);
    }

    public void surnameTest(String str)
    {
        boolean test = dataBaseValidator.surnameValidate(str);
        System.out.println("TEST: " + test);
    }



}
