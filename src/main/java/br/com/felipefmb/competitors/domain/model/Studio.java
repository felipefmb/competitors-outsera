package br.com.felipefmb.competitors.domain.model;

import java.math.BigInteger;

public class Studio implements Domain {
    BigInteger id;
    String name;

    public BigInteger getId() {
        return id;
    }

    public Studio setId(BigInteger id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Studio setName(String name) {
        this.name = name;
        return this;
    }
}
