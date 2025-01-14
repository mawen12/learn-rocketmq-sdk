package com.mawen.learn.rocketmq.message;

import org.apache.rocketmq.common.constant.PermName;

/**
 * 权限，读-R，写-W，继承-X
 * <ul>
 *     <li>rwx -> 0111</li>
 *     <li>rw -> 0110</li>
 *     <li>r -> 0100</li>
 *     <li>w -> 0010</li>
 *     <li>x -> 0001</li>
 * </ul>
 */
public class PermDemo {

    public static void main(String[] args) {
        System.out.println(1 << PermName.INDEX_PERM_PRIORITY); // 8
        System.out.println(1 << PermName.INDEX_PERM_READ); // 4
        System.out.println(1 << PermName.INDEX_PERM_WRITE); // 2
        System.out.println(1 << PermName.INDEX_PERM_INHERIT); // 1

        int rwx = 7; // 0111
        System.out.println("rw is " + (PermName.isReadable(rwx) ? "readable" : "non-readable") + ", " + (PermName.isWriteable(rwx) ? "writable" : "non-writable"));

        System.out.println(12 % 5);
    }
}
