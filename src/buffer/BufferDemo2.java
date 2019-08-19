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

    @Test
    public void test4() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(200);
        System.out.println(byteBuffer.hasArray());
        byteBuffer.put((byte) 1);
        System.out.println(byteBuffer.hasArray());

        ByteBuffer direct = ByteBuffer.allocateDirect(100);
        System.out.println(direct.hasArray());
        direct.put((byte) 0);
        System.out.println(direct.hasArray());
    }

    @Test
    public void test5() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);

        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());

        byteBuffer.position(2);
        // 计算出position和limit的元素个数
        System.out.println(byteBuffer.remaining());

    }

    @Test
    public void test6() {
        byte[] bytes = new byte[]{1,2,3,4,5,6};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        while (byteBuffer.hasRemaining()) {
            //Relative <i>get</i> method.  Reads the byte at this buffer's
            //     * current position, and then increments the position.
            System.out.println(byteBuffer.get());
        }
        System.out.println(byteBuffer);
        byteBuffer.clear();
        System.out.println(byteBuffer);
    }

    @Test
    public void test7() {
        byte[] bytes = new byte[]{1,2,3,4,5,6};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        System.out.printf("capacity=%d, position=%d,  limit=%d\n",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit());

        byteBuffer.position(1);
        byteBuffer.limit(3);
        byteBuffer.mark();
        System.out.printf("capacity=%d, position=%d,  limit=%d\n",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit());

        //冲绕此缓冲区（不改变limit）
        //        position = 0;
        //        mark = -1;
        byteBuffer.rewind();
        System.out.printf("capacity=%d, position=%d,  limit=%d\n",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit());

        //因为丢弃了mark  这里会抛出异常
        byteBuffer.reset();
    }

    @Test
    public void test8() {
        byte[] bytes = new byte[]{1,2,3,4,5,6};
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        boolean b = byteBuffer.hasArray();
        System.out.printf("hasArray=%s\n",b);
        System.out.printf("arrayOffset=%d\n",byteBuffer.arrayOffset());
    }

     @Test
    public void test9() {
         ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
         byte[] bytes = new byte[]{1,2,3,4,5,6};
         byteBuffer.put(bytes);
        boolean b = byteBuffer.hasArray();
        System.out.printf("hasArray=%s\n",b);
        //  * <p> Invoke the {@link #hasArray hasArray} method before invoking this
         //     * method in order to ensure that this buffer has an accessible backing
         //     * array.  </p>
         //必须要有备份数组，如果是直接放入内存的话  会抛出异常java.lang.UnsupportedOperationException
         // 所以调用arrayOffset方法前，先调用hasArray方法去校验一次
        System.out.printf("arrayOffset=%d\n",byteBuffer.arrayOffset());
    }



}
