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
}
