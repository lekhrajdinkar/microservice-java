package more.kafka.spring;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import java.util.Map;

// props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "more.kafka.spring.CustomPartitioner"); 👈🏻

public class customPartition implements Partitioner
{
    @Override
    public int partition(
            String topic,
            Object key,    byte[] keyBytes,
            Object value,  byte[] valueBytes,
            Cluster cluster)
    {
        // sample quick hardcode partition logic :(
        if (keyBytes == null || keyBytes.length == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> map) {}
}
