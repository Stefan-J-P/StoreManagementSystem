package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.CategoryDto;
import jan.stefan.hibernate.model.Category;

public class CategoryJsonConverter extends JsonConverter<CategoryDto>
{
    public CategoryJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
