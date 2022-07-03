package gg.clouke.alpha.util.atomic;

/**
 * @author Lucanius
 * @since June 02, 2022.
 * Alpha - All Rights Reserved.
 */
public class FinalNumber extends Number {

    private int value;

    public FinalNumber() {
        this.value = 0;
    }

    public FinalNumber(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public int set(int value) {
        return this.value = value;
    }

    public int getAndIncrease() {
        return value++;
    }

    public int increaseAndGet() {
        return ++value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
