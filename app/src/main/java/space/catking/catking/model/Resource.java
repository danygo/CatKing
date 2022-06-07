package space.catking.catking.model;

import androidx.annotation.Nullable;
import space.catking.catking.vo.Status;

public class Resource<T> {
    private final Status status;
    private final T data;
    private final Throwable error;

    public Resource(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public static <T> Resource<T> ok(@Nullable T data) {
        return new Resource<>(Status.OK, data, null);
    }

    public static <T> Resource<T> error(Throwable error, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}
