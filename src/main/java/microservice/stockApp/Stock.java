package microservice.stockApp;
import lombok.Data;

@Data
public class Stock
{
    private Long id;
    private String name;
    private String symbol;
    private String value;
    private String sector;
    private String industry;
}
