package org.example.basic;

import org.junit.Test;

public class CharSequenceDemo{


    @Test
    public void testSequenceDemo() {


        String password = "User123_Admin456";
        // 统计字符串中数字的个数
        long count = password.chars().filter(Character::isDigit)
                .count();
        System.out.println("数字个数: " + count); // 输出: 6;
    }
    @Test
    public void testSequenceDemo02() {
//        处理 ASCII 值的转换
        String secret = "HAL";
        secret.chars().map(c->c+1)
                .forEach(c-> System.out.print((char) c));

    }
    @Test
    public void testSequenceDemo03() {
        //进阶用法：字符去重与排序
        String input = "banana";
        String string = input.chars().distinct().sorted()
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append).toString();
        System.out.println("处理结果: " + string);


    }

}