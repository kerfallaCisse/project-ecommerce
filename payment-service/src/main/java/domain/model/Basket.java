package domain.model;

import java.util.List;
import lombok.Data;


@Data
public class Basket {
    public List<Product> products;
    public String current;
}
