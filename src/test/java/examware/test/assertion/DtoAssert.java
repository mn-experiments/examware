package examware.test.assertion;

import org.assertj.core.api.AbstractAssert;

public class DtoAssert<T> extends AbstractAssert<DtoAssert<T>, T> {
    public DtoAssert(T t, Class<?> selfType) {
        super(t, selfType);
    }
    public DtoAssert(T t) {
        super(t, DtoAssert.class);
    }

    /**
     * Compares the data of the DTOs ignoring the ID field
     */
    public DtoAssert<T> hasSameDataAs(T other) {
        usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(other);

        return this;
    }
}
