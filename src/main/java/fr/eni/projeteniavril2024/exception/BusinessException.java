package fr.eni.projeteniavril2024.exception;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private List<String> keys = new ArrayList<>();

    public BusinessException() {
        super();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public List<String> getKeys() {
        return keys;
    }

    public void add(String key) {
        this.keys.add(key);
    }
}
