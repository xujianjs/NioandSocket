package buffer;

import org.junit.Test;

import java.nio.*;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName BufferDemo1.java
 * @Description TODO
 * @createTime 2019年08月16日 08:57:00
 */
public class BufferDemo1 {

    @Test
    public void test1() {
        byte[] byteArray = new byte[]{1, 2, 3};
        short[] shortArray = new short[]{1, 0};
        int[] intArray = new int[]{1, 3, 8, 10};
        long[] longArray = new long[]{2, 5, 8, 9};
        float[] floatArray = new float[]{1, 2, 3};
        double[] doubleArray = new double[]{1, 5, 6};
        char[] charArray = new char[]{'a', 'b', 'c'};

        //通过抽象类Buffer 包装字节数组  得到实例化缓冲对象（因为不管是最顶层的buffer还是7个子类都是抽象类  无法实例化 类似于工厂方法模式）
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        ShortBuffer shortBuffer = ShortBuffer.wrap(shortArray);
        IntBuffer intBuffer = IntBuffer.wrap(intArray);
        LongBuffer  longBuffer = LongBuffer.wrap(longArray);
        FloatBuffer floatBuffer = FloatBuffer.wrap(floatArray);
        DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubleArray);
        CharBuffer charBuffer=CharBuffer.wrap(charArray);

        System.out.printf("byteBuffer:%s,byteBuffer.capacity=%s\n",byteBuffer.getClass().getName(),byteBuffer.capacity());
        System.out.printf("shortBuffer:%s,shortBuffer.capacity=%s\n",shortBuffer.getClass().getName(),shortBuffer.capacity());
        System.out.printf("intBuffer:%s,intBuffer.capacity=%s\n",intBuffer.getClass().getName(),intBuffer.capacity());
        System.out.printf("longBuffer:%s,longBuffer.capacity=%s\n",longBuffer.getClass().getName(),longBuffer.capacity());
        System.out.printf("floatBuffer:%s,floatBuffer.capacity=%s\n",floatBuffer.getClass().getName(),floatBuffer.capacity());
        System.out.printf("doubleBuffer:%s,doubleBuffer.capacity=%s\n",doubleBuffer.getClass().getName(),doubleBuffer.capacity());
        System.out.printf("charBuffer:%s,charBuffer.capacity=%s\n",charBuffer.getClass().getName(),charBuffer.capacity());
    }
}
