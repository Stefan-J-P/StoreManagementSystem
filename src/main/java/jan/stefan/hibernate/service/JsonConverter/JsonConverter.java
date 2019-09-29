package jan.stefan.hibernate.service.JsonConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jan.stefan.hibernate.exceptions.MyException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T>
{
    private final String jSonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jSonFilename)
    {
        this.jSonFilename = jSonFilename;
    }

    // CONVERSION FROM OBJECT T TO JSON FORMAT --------------------------------------------------------------------
    public void toJson(final T element)
    {
        try (FileWriter fileWriter = new FileWriter(jSonFilename))
        {
            if (element == null)
            {
                throw new NullPointerException(jSonFilename);
            }
            gson.toJson(element, fileWriter);

        } catch (Exception e)
        {
            // re-throw
            throw new MyException(e.getMessage());
        }
    }

    // CONVERSION FROM JSON FORMAT TO OBJECT T -------------------------------------------------------------------
    public Optional<T> fromJson()
    {
        try (FileReader fileReader = new FileReader(jSonFilename))
        {
            return Optional.of(gson.fromJson(fileReader, type));

        } catch (IOException e)
        {
            System.err.println("FROM JSON - JSON FILENAME EXCEPTION");
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new MyException("JSON CONVERTER: FROM JSON: " + e.getMessage());
        }
        return Optional.empty();
    }



}
