package store.common.handler;

public class InputRetryHandler {
    private final int maxRetries;

    public InputRetryHandler(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public <T> T execute(SupplierWithException<T> supplier) {
        int attempts = 0;

        while (attempts < maxRetries) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                attempts++;
            }
        }

        throw new IllegalArgumentException();
    }

    @FunctionalInterface
    public interface SupplierWithException<T> {
        T get() throws IllegalArgumentException;
    }
}
