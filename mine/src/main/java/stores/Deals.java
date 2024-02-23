package stores;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@Table
public class Deals {
    @Id
    private Long id;
    private Long typeId;

    @NotBlank(message = "Тикер не может быть пустым.")
    @Size(min = 3, message = "Должно быть больше 3х символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Тикер может содержать только буквы.")
    private String ticker;
    
    @NotBlank(message = "Номер заказа не может быть пустым.")
    @Pattern(regexp = "\\d{6}", message = "Номер заказа должен состоять из 6 цифр.")
    private String orderNumber;
    
    @NotBlank(message = "Номер сделки не может быть пустым.")
    @Pattern(regexp = "\\d{6}", message = "Номер сделки должен состоять из 6 цифр.")
    private String dealNumber;

    @NotNull(message = "Количество ценных бумаг сделки не может быть пустым.")
    @Max(value = 10000, message = "Максимальное значение для количества ценных бумаг - 10000")
    private Integer dealQuantity;

    @NotNull(message = "Цена сделки не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealPrice;

    @NotNull(message = "Цена сделки не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealTotalCost;

    @NotBlank(message = "Трейдер сделки не может быть пустым.")
    private String dealTrader;

    @NotNull(message = "Цена сделки не может быть пустой.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена сделки должна быть больше 0")
    private Double  dealCommission;
}










