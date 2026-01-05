package org.example.basic;

import org.junit.Test;

import java.util.Objects;

public class CheckIndexDemo {


    @Test
    public void testSequenceDemo() {
        StringCheckIndex buffer = new StringCheckIndex(5);
        buffer.add(10.1);
        buffer.add(20.2);
        buffer.add(30.3);

        // 正确访问
        System.out.println("数据 1: " + buffer.get(1)); // 输出 20.2

        // 真实错误演示：尝试访问还没存入数据的索引
        try {
            System.out.println("数据 4: " + buffer.get(4));
        } catch (IndexOutOfBoundsException e) {
            // 异常信息会自动告诉你：Index 4 out of bounds for length 3
            System.err.println("访问失败: " + e.getMessage());
        }
    }




}

class StringCheckIndex {
    private final double[] data;
    private int size = 0;
    private int head = 0;

    public StringCheckIndex(int capacity) {
        this.data = new double[capacity];
    }

    /**
     * 添加数据
     */
    public void add(double value) {
        data[head] = value;
        head = (head + 1) % data.length;
        if (size < data.length) size++;
    }

    /**
     * 获取指定位置的数据
     * 这里的逻辑必须保证 index 在当前有效数据范围内
     */
    public double get(int index) {
        // 【核心应用点】
        // 1. 验证 index 是否在 [0, size) 之间
        // 2. 如果失败，自动抛出如 "Index 5 out of bounds for length 3" 的异常
        // 3. 该方法被 JVM 优化，几乎没有性能损耗
        Objects.checkIndex(index, size);

        // 计算实际在数组中的物理位置
        int actualPos = (head - size + index + data.length) % data.length;
        return data[actualPos];
    }

    public int getCurrentSize() {
        return size;
    }
}