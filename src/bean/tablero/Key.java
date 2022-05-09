package bean.tablero;

public class Key {

    private final Double k1;
    private final Double k2;

    public Key(Double k1, Double k2) {
        this.k1 = k1;
        this.k2 = k2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return k1.equals(key.k1) && k2.equals(key.k2);
    }

    @Override
    public int hashCode() {
        return k1.hashCode()+k2.hashCode();
    }
    public double getX() {
    	return k1;
    }
    public double getY() {
    	return k2;
    }

}
