package fundamentals.java_8_and_above.streamProcessing;

import java.util.stream.Stream;

// Producer
@FunctionalInterface
public interface FnIStreamGen<T>{
    public Stream<T> generate();
}