package org.example.basic;


import org.junit.Test;

import java.awt.*;
import java.math.BigDecimal;

public class RecordDemo {

    @Test
    public void testRecord() {

        Point p = new Point(123, 456);
        System.out.println(p.x());
        System.out.println(p.y());
        System.out.println(p);
    }

}

record Point(int x, int y) {


    public static Point of() {
        return new Point(0, 0);
    }

    public Point(int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
        this.x = x;
        this.y = y;
    }
    // 无参构造函数
    public Point() {
        this(0, 0); // 必须显式委托给规范构造函数 Point(int x, int y)
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public String toString() {
        return String.format("Point[x=%s, y=%s]", x, y);
    }

    public boolean equals(Object o) {
        return false;
    }
    public int hashCode() {
       return 0;
    }
}

record Product(
        String name,
        BigDecimal price,
        String category,
        int quantity
) {
}
