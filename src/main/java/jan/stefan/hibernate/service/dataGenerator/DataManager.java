package jan.stefan.hibernate.service.dataGenerator;

import java.io.FileReader;
import java.util.*;

public class DataManager
{
    private final static Random rnd = new Random();

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

    public String generateCustomerName(List<String> myList)
    {
        return myList.get(rnd.nextInt(myList.size()));
    }





}
