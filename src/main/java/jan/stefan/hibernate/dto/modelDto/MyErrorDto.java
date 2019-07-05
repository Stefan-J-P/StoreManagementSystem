package jan.stefan.hibernate.dto.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyErrorDto
{
    private Long id;
    private LocalDateTime dateTime;
    private String message;
}
