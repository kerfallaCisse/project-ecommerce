package domain.model;

import java.util.List;
import lombok.Data;


@Data
public class Basket {
    private List<ProductBasket> products;
}
