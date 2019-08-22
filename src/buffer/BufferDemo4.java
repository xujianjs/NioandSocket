package buffer;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName BufferDemo4.java
 * @Description TODO
 * @createTime 2019年08月22日 08:48:00
 */
public class BufferDemo4 {
    @Test
    public void test1() {
        byte[] byte1=new byte [] {1,2,3,4,5,6,7,8};
        byte[] byte2=new byte [] {55,66,77,88};

        //开辟10个空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        //把1,2,3,4,5,6,7,8放入缓冲区前8个位置
        byteBuffer.put(byte1);
        byte[] array = byteBuffer.array();
        byteBuffer.position(2);

//        for (byte b : array) {
//            System.out.println(b);
//        }
//        System.out.println(byteBuffer.get());
        //offset 是在源数组中的偏移量
        //length 在原数组中读取的个数
        byteBuffer.put(byte2, 2, 2);

        for (byte b : array) {
            System.out.println(b);
        }

        //创建新的数组，目的是把缓冲区的数据导出来
        byte[] byteOut = new byte[byteBuffer.capacity()];
        byteBuffer.position(0);
        //offset 目标数组的偏移量
        //length 从缓冲区position位置开始读取的个数
        byteBuffer.get(byteOut, 4, 4);
        System.out.println("输出到新的数组中");
        for (byte b : byteOut) {
            System.out.println(b);
        }


    }

    @Test
    public void test2() {
        String[] array = {"a", "b", "c", "d", "e"};

        //Arrays.stream
        Stream<String> stream1 = Arrays.stream(array);
        stream1.forEach(x -> System.out.println(x));

        //Stream.of
        Stream<String> stream2 = Stream.of(array);
        stream2.forEach(x -> System.out.println(x));

    }

    @Test
    public void test3() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);

        byteBuffer.put((byte) 'a');//占1个字节
        byteBuffer.putChar('a');//占2个字节
        byteBuffer.putInt(1);//占4个字节
        byteBuffer.putShort((short)1);//占2个字节
        System.out.println("记录long:2L位置"+byteBuffer.position());
        byteBuffer.putLong(2L);//占8个字节
        System.out.println("记录float:1.5f位置"+byteBuffer.position());
        byteBuffer.putFloat(1.5f);//占4个字节
        byteBuffer.putDouble(2.2);//占8个字节

        byte[] array = byteBuffer.array();
        System.out.println(byteBuffer.position());

        System.out.println((char)byteBuffer.get(0));
        System.out.println(byteBuffer.getChar(1));
        System.out.println(byteBuffer.getInt(3));
        System.out.println(byteBuffer.getLong(9));
        System.out.println(byteBuffer.getFloat(17));
    }

    @Test
    public void test4() {
        byte[] byteArray = new byte[]{1,2,3,4,5,6,7,8};
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(byteArray);
        byteBuffer1.position(5);
        byteBuffer1.limit(6);

        System.out.printf("A----position:%d limit:%d capacity:%d ",byteBuffer1.position(),byteBuffer1.limit(),byteBuffer1.capacity());
        System.out.println();
        ByteBuffer byteBuffer2 = byteBuffer1.slice();
        System.out.printf("byteBuffer1-----arrayOffset:%d\n",byteBuffer1.arrayOffset());

        System.out.printf("B----position:%d limit:%d capacity:%d\n",byteBuffer2.position(),byteBuffer2.limit(),byteBuffer2.capacity());


        byteBuffer1.put((byte) 111);
//        byteBuffer1.put((byte) 222);//抛出java.nio.BufferOverflowException  -----  调用slice后 (共享序列)实际容量变为了1  只能写入一个字节

        byte[] array1 = byteBuffer1.array();
        byte[] array2 = byteBuffer2.array();

        System.out.println();
        for (byte b : array1) {
            System.out.print(b+" ");
        }
        System.out.println();

        for (byte b : array2) {
            System.out.print(b+" ");
        }

    }


    @Test
    public void test5() {
        byte[] byteArray1 = new byte[]{1,2,3,4,5,6,7,8};
        ByteBuffer byteBuffer1 = ByteBuffer.wrap(byteArray1);
        byteBuffer1.position(5);
        ByteBuffer byteBuffer2 = byteBuffer1.slice();

        //在使用 slice() 方法后，再调用 arrayOffset()方法 ，会出现返回值<>0 的情况
        System.out.printf("byteBuffer2----arrayOffset:%d\n",byteBuffer2.arrayOffset());
        //byteBuffer2的第一个元素相当于byteArray1数组中索引为5的元素
        System.out.println(byteBuffer2.get());//6
    }
}
