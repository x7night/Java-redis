package org.example.datastruct;

import org.example.util.Utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class SDS implements CharSequence {
    private static Charset charset = Charset.forName("UTF-8");
    /**
     * 长度 = buf数组中已使用的空间
     */
    private int len;
    /**
     * 剩余长度
     */
    private int free;
    /**
     * 缓冲区
     */
    private char[] buf;

    public SDS(){
        len = 0;
        free = 0;
        buf = new char[0];
    }

    public SDS(ByteBuffer buffer, int cap) {
        buf = new char[cap];
        // 读取字节
        readFromBufferAndSetChar(buffer);
    }

    public SDS(char[] chars) {
        len = chars.length;
        free = 0;
        buf = chars;
    }

    public SDS(CharSequence cs) {
        this(cs.toString().toCharArray());
    }

    private void readFromBufferAndSetChar(ByteBuffer buffer) {
        buffer.flip();
        int i = len;
        CharBuffer charbuffer = charset.decode(buffer);
        while (charbuffer.hasRemaining()) {
            buf[i++] = charbuffer.get();
        }
        // 清空Buffer
        buffer.clear();
//        buffer.flip();
        len = i;
        free = buf.length - len;
    }


    /**
     * 扩展字符串
     *
     * @param buffer 字节buffer
     * @param n      字节数量
     * @return sds
     */
    public SDS append(ByteBuffer buffer, int n) {
        if (free < n) {
            buf = Arrays.copyOf(buf, Utils.minPowFor(n + len));
        }
        readFromBufferAndSetChar(buffer);
        return this;
    }
    /**
     * 扩展字符串
     *
     * @param s 字符串
     * @return sds
     */
    public SDS append(String s) {
        if (free < s.length()) {
            buf = Arrays.copyOf(buf, Utils.minPowFor(s.length() + len));
            free = buf.length - len;
        }
        for (Character c : s.toCharArray()) {
            buf[len++] = c;
        }
        free -= s.length();
        return this;
    }

    /**
     * 移除前n个字符
     *
     * @param n 字符个数, 从下标0开始
     * @return 超出字符数组大小则失败, 否则成功
     */
    public boolean remove(int n) {
        if (n > len + free) {
            return false;
        }
        if (len == 0) {
            return true;
        }
        int i = 0;
        for (int start = n; start < len; start++) {
            buf[i++] = buf[start];
        }
        len = i;
        free = buf.length - len;
        return true;
    }

    /**
     * 清空字符(长度置0)
     * @return
     */
    public boolean clear() {
        free += len;
        len = 0;
        return true;
    }

    public boolean replace(String s, int fromIndex) {
        if (fromIndex >= len) {
            return false;
        }
        if (fromIndex + s.length() > len) {
            return false;
        }
        for (Character c : s.toCharArray()) {
            buf[fromIndex++] = c;
        }
        return true;
    }

    @Override
    public int length() {
        return len;
    }

    @Override
    public char charAt(int index) throws IndexOutOfBoundsException {
        if (index > len || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return buf[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new SDS(Arrays.copyOfRange(buf, start, end));
    }

    @Override
    public String toString() {
        return new String(Arrays.copyOfRange(buf, 0, len));
    }

    public char[] toCharArray() {
        return Arrays.copyOfRange(buf, 0, len);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDS that = (SDS) o;
        return Arrays.equals(buf, 0, len, that.buf, 0, that.len);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(Arrays.copyOf(buf, len));
    }
}
