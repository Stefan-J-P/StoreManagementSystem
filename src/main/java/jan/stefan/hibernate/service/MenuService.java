package jan.stefan.hibernate.service;

import jan.stefan.hibernate.exceptions.MyException;

public class MenuService
{

    public void mainMenu()
    {
        while (true)
        {
            try
            {




            } catch (MyException e)
            {
                System.out.println("*************************** EXCEPTION ********************************************");
                System.err.println(e.getMessage());
                System.out.println("**********************************************************************************");
            }
        }




    }

}
