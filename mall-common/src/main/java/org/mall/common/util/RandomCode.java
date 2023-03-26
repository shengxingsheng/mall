package org.mall.common.util;

import java.util.Random;

/**
 * 随机验证码
 *
 * @author sxs
 * @since 2023/2/11
 */
public class RandomCode {
    public static final Random random = new Random();

    private RandomCode() {
    }

    /**
     * 获取6位数字验证码
     * @return
     */
    public static String getSixNumCode() {
        return String.valueOf(nextInt(100000, 1000000));
    }

    public static int nextInt(int origin, int bound) {
        int n = bound - origin;
        if (n > 0) {
            return random.nextInt(n) + origin;
        }else {
            return 0;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(getSixNumCode());
        }
    }
}
