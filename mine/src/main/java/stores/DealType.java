package stores;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@Table
public class DealType {
    @Id
    private Long id;
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Тип может содержать только буквы.")
    private String type;
}


