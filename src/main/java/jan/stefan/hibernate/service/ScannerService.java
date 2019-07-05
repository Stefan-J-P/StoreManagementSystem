package jan.stefan.hibernate.service;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.enums.EGuarantee;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ScannerService
{
    private Scanner sc = new Scanner(System.in);
    BigDecimal DISCOUNT = new BigDecimal(0.15);

    public String getString(String message)
    {
        try
        {
            System.out.println(message);
            return sc.nextLine();
        } catch (Exception e)
        {
            throw new MyException("GET STRING EXCEPTION: " + e.getMessage());
        }
    }

    public Integer getInt(String message)
    {
        System.out.println(message);
        String text = sc.nextLine();
        if (!text.matches("\\d+"))
        {
            throw new MyException("GET INT VALUE EXCEPTION: " + text);
        }
        return Integer.parseInt(text);
    }

    public BigDecimal getBigDecimal(String message)
    {
        try
        {
            System.out.println(message);
            return new BigDecimal(sc.nextLine());

        } catch (Exception e)
        {
            throw new MyException("GET BIG DECIMAL EXCEPTION: " + e.getMessage());
        }

    }

    public LocalDate getDate(String message)
    {
        try
        {
            System.out.println(message);
            return LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        } catch (Exception e)
        {
            throw new MyException("GET DATE EXCEPTION: " + e.getMessage());
        }
    }

    public LocalTime getTime(String message)
    {
        try
        {
            System.out.println(message);
            return LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        } catch (Exception e)
        {
            throw new MyException("GET TIME EXCEPTION: " + e.getMessage());
        }
    }

    public LocalDate generateExpirationDate()
    {
        try
        {
            return LocalDate.now().plusDays(30);

        } catch (Exception e)
        {
            throw new MyException("GENERATE DATE EXCEPTION: " + e.getMessage());
        }
    }


    public EGuarantee getEGuarantee(/*String message*/)
    {
        System.out.println("Enter EGuarantee option: ");
        String text = sc.nextLine();
        return EGuarantee.valueOf(text);
    }

/*    public BigDecimal generateDiscount()
    {
        try
        {
            BigDecimal min = new BigDecimal(0.15);
            BigDecimal max = new BigDecimal(0.5);
            BigDecimal rnd = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));

            //return rnd.setScale(2, BigDecimal.ROUND_HALF_UP);
            //return rnd.setScale(2, 2);
            return  rnd;
        } catch (Exception e)
        {
            throw new MyException("GENERATE DISCOUNT EXCEPTION: " + e.getMessage());
        }
    }*/



    public void close ()
    {
        if (sc != null)
        {
            sc.close();
        }
    }



}
