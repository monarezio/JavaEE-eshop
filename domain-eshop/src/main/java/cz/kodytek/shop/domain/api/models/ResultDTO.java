package cz.kodytek.shop.domain.api.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultDTO<T extends Serializable> implements Serializable {

    private Map<String, List<String>> errors;
    private T data;

    T get() {
        return data;
    }

    public ResultDTO(Map<String, List<String>> errors, T data) {
        this.errors = errors;
        this.data = data;
    }

    public ResultDTO(HashMap<String, List<String>> errors) {
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
