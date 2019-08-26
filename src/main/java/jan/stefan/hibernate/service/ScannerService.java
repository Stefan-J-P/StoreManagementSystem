package jan.stefan.hibernate.service;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.enums.EGuarantee;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
            e.printStackTrace();
            throw new MyException("SCANNER SERVICE - GET STRING EXCEPTION: " + e.getMessage());
        }
    }

    public Integer getInt(String message)
    {
        System.out.println(message);
        String text = sc.nextLine();
        if (!text.matches("\\d+"))
        {
            System.out.println("-----" + text + "-----");
            throw new MyException("SCANNER SERVICE - GET INT VALUE EXCEPTION: " + text);
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
            throw new MyException("SCANNER SERVICE - GET BIG DECIMAL EXCEPTION: " + e.getMessage());
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
            throw new MyException("SCANNER SERVICE - GET DATE EXCEPTION: " + e.getMessage());
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
            throw new MyException("SCANNER SERVICE - GET TIME EXCEPTION: " + e.getMessage());
        }
    }

    public LocalDate generateExpirationDate()
    {
        try
        {
            return LocalDate.now().plusDays(30);

        } catch (Exception e)
        {
            throw new MyException("SCANNER SERVICE - GENERATE DATE EXCEPTION: " + e.getMessage());
        }
    }

    public Long getLong(String message)
    {
        System.out.println(message);
        String text = sc.nextLine();
        if (!text.matches("\\d+"))
        {
            throw new MyException("SCANNER SERVICE - GET LONG VALUE EXCEPTION: " + text);
        }
        return Long.parseLong(text);
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

    // =========================================================================================================================================_
    /*
    static Set<Color> getColors() {

        AtomicInteger counter = new AtomicInteger(1);

        Arrays
                .stream(Color.values())
                .forEach(color -> System.out.println(counter.getAndIncrement() + ". " + color));

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter color numbers separating them with comma");
        String colorNumbers = sc.nextLine();

        if ( !colorNumbers.matches("(\\d+,)*\\d+") ) {
            throw new IllegalStateException("color numbers has incorrect format");
        }

        return Arrays
                .stream(colorNumbers.split(","))
                .map(number -> {

                    int n = Integer.parseInt(number);
                    if ( n < 0 || n >= Color.values().length ) {
                        throw new IllegalStateException("no color with number " + n);
                    }

                    return Color.values()[n - 1];
                })
                .collect(Collectors.toSet());           */
    // =========================================================================================================================================_


    public Set<EGuarantee> getGuarantees(String message)
    {
        AtomicInteger counter = new AtomicInteger(1);
        Arrays.stream(EGuarantee.values()).forEach(g -> System.out.println(counter.getAndIncrement() + ", " + g));
        System.out.println("Enter guarantee values separating them with comma");
        String guaranteeValues = sc.nextLine();
        /*
        if (!guaranteeValues.matches("[A-Z_]*,[A-Z_]+"))
        {
            throw new MyException("SCANNER SERVICE: getGuarantees(): Regex exception");
        }   */
        return Arrays.stream(guaranteeValues.split(",")).map(number -> {
            int n = Integer.parseInt(number);
            if (n < 0 || n >= EGuarantee.values().length )
            {
                throw new MyException("SCANNER SERVICE: getGuarantees(): No Guarantee as: " + n);
            }
            return EGuarantee.values()[n-1];
        }).collect(Collectors.toSet());
    }






    public void close ()
    {
        if (sc != null)
        {
            sc.close();
        }
    }



}


















