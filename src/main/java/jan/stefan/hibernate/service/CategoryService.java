package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.CategoryDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.repository.repositoryInterfaces.CategoryRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryDto addOrUpdate(CategoryDto categoryDto)
    {
        if (categoryDto == null)
        {
            throw new MyException("CATEGORY SERVICE: categoryDto object is null");
        }

        Category category = ModelMapper.fromCategoryDtoToCategory(categoryDto);
        return ModelMapper.fromCategoryToCategoryDto(categoryRepository
                .saveOrUpdate(category)
                .orElseThrow(() -> new MyException("CATEGORY SERVICE: cannot addOrUpdate() category")));
    }

    public List<CategoryDto> findAll()
    {
        return categoryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        categoryRepository.delete(id);
    }

    public CategoryDto findOneById(Long id)
    {
        return categoryRepository
                .findById(id)
                .map(ModelMapper::fromCategoryToCategoryDto)
                .orElseThrow(() -> new MyException("CATEGORY SERVICE: findOneById() cannot find id: " + id));
    }



}
