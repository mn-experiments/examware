package examware.test;

import examware.test.assertion.DtoAssert;
import org.assertj.core.api.ListAssert;

import java.util.List;

public abstract class Assertions {
    public static <T> ListAssert<T> assertThat(List<T> t) {
        return org.assertj.core.api.Assertions.assertThat(t);
    }

    public static <T> DtoAssert<T> assertThat(T dto) {
        return new DtoAssert<>(dto);
    }
}
