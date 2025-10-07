package springbatch.steps.processer;


import springbatch.entity.Order;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class OrderWriter implements ItemWriter<Order> {

    /*@Override
    public void write(List<? extends Order> orders) throws Exception {
        // Simulate writing processed orders (Replace with actual implementation)
        orders.forEach(order -> System.out.println("Writing Order: " + order));
    }*/

    @Override
    public void write(Chunk<? extends Order> chunk) throws Exception {
        chunk.forEach(order -> System.out.println("Writing Order: " + order));
    }
}

