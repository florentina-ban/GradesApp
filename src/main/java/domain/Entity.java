package domain;

import java.io.Serializable;

public abstract class Entity<ID> implements Serializable {
    private static final long serialVersionUID= 18970750310610700L;

    private ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }
}
