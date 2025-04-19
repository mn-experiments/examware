package concept;

import java.util.Objects;

public abstract class PersistedObject {

    protected abstract Long getId();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersistedObject that = (PersistedObject) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
