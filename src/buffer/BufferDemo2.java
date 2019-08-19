package buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.junit.Test;

public class BufferDemo2 {

    @Test
    public void test1() {
        // 分配出一个直接缓冲区
        // 大多数情况下往磁盘写入数据都是把数据放在jvm的中间缓冲区的  然后再往磁盘写入的 这样效率就很低
        //  直接缓冲区就没有暂存  直接输出到磁盘
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        //  利用isDirect方法判断是否是直接缓冲区
        boolean direct = byteBuffer.isDirect();
        System.out.println(direct);

    }

    @Test
    public void test2() {
        CharBuffer charBuffer = CharBuffer.allocate(20);
        System.out.printf("A position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());

        //写入11个字（position从0开始）
        charBuffer.put("我即将做出我的重大选择");
        System.out.printf("B position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);//position跑到缓冲最后 无法读取数据

        //位置还原成0
        charBuffer.position(0);//又可以重新读写
        System.out.printf("C position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);

        for (int i = 0; i < charBuffer.limit(); i++) {
            //共分配长度为20的数组  用去了11个  后面会打印出20-11=9个“”
            System.out.printf("%s--------------------i=%d\n",charBuffer.get(),i);
        }

        System.out.println("上面的代码是错误的读取数据的方法");

        System.out.println("下面的代码才是正确的读取数据的方法");

        System.out.printf("D position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        //还原缓冲区的状态
        charBuffer.clear();
        System.out.printf("E position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);

        //继续写入
        charBuffer.put("我是江苏人");
        System.out.printf("F position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);

        //设置for循环结束的位置
        charBuffer.limit(charBuffer.position());
        charBuffer.position(0);


        System.out.printf("G position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);

        for (int i = 0; i <charBuffer.limit() ; i++) {
            System.out.println(charBuffer.get());
        }

    }


    @Test
    public void test3() {
        //test2()方法比较繁琐  使用flip() 更为高效
        CharBuffer charBuffer = CharBuffer.allocate(20);
        System.out.printf("A position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());

        //写入11个字（position从0开始）
        charBuffer.put("我即将做出我的重大选择");
        System.out.printf("B position=%d, limit=%d\n", charBuffer.position(), charBuffer.limit());
        System.out.println(charBuffer);//position跑到缓冲最后 无法读取数据

        //还原缓冲区的状态
        //         position = 0;
        //        limit = capacity;
        //        mark = -1;
        charBuffer.clear();

        charBuffer.put("我是江苏人");
        charBuffer.flip();

        for (int i = 0; i <charBuffer.limit() ; i++) {
            System.out.println(charBuffer.get());
        }



    }

}
