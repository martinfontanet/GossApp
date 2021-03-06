package gossapp.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParametersPasser<A,B,C,D> {
    private final A a;
    private final B b;
    private final C c;
    private final D d;

    public ParametersPasser(@JsonProperty("a") A a, @JsonProperty("b") B b, @JsonProperty("c") C c, @JsonProperty("d") D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }

    public D getD() {
        return d;
    }
}
