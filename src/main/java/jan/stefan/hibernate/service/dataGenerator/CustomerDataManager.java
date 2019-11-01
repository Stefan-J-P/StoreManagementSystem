package jan.stefan.hibernate.service.dataGenerator;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.service.CustomerService;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.util.*;

@RequiredArgsConstructor
public class CustomerDataManager
{
    private final DataBaseValidator dataBaseValidator;
    private final DataManager dataManager;
    private final CustomerService customerService;

    public void addEmptyCustomerToDataBase()
    {
        customerService.addOrUpdate(CustomerDto.builder().build());
    }

    public String generateEmailFromTwoStrings(String name, String surname)
    {
        return name.toLowerCase() + "." + surname.toLowerCase() + "@wp.pl";
    }

    public List<String> generateEmails()
    {
        CustomerDto customerDto = new CustomerDto();

        List<String> names = dataManager.readFile("names.txt");
        List<String> surnames = dataManager.readFile("surnames.txt");
        List<String> emails = new ArrayList<>();

        for (int i = 0; i < names.size() ; ++i)
        {
            emails.add(generateEmailFromTwoStrings(names.get(i), surnames.get(i)));
        }
        return emails;
    }

    public CustomerDto generateOneCustomer(String namesFile, String surnamesFile, String countryFile)
    {
        List<String> names = dataManager.readFile(namesFile);
        List<String> surnames = dataManager.readFile(surnamesFile);
        List<String> countries = dataManager.readFile(countryFile);

        String name;
        String surname;
        String email;
        do
        {
            name = dataManager.generateRandomStringFromList(names);
            System.out.println("NAME: " + name);
            surname = dataManager.generateRandomStringFromList(surnames);
            System.out.println("SURNAME: " + surname);
            email = generateEmailFromTwoStrings(name, surname);
            System.out.println("EMAIL: " + email);
        } while (dataBaseValidator.nameValidate(name) && dataBaseValidator.surnameValidate(surname) && dataBaseValidator.emailValidate(email));

        Integer age = dataManager.generateRandomAge();
        CountryDto countryDto = CountryDto.builder().name(dataManager.generateRandomStringFromList(countries)).build();

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(name);
        customerDto.setSurname(surname);
        customerDto.setEmail(email);
        customerDto.setAge(age);
        customerDto.setCountryDto(countryDto);
        return customerDto;
    }

    public Set<CustomerDto> generateManyCustomers(String namesFile, String surnamesFile, String countryFile)
    {
        //List<CustomerDto> customers = new ArrayList<>();
        CustomerDto customer = new CustomerDto();
        Set<CustomerDto> customers = new HashSet<>();

        for (int i = 0; i < 100; ++i)
        {
            customer = generateOneCustomer(namesFile, surnamesFile, countryFile);
            customers.add(customer);
        }
        return customers;
    }







}
