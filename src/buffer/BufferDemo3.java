package buffer;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * 缓冲区分为：
 * 直接缓冲区------调用工厂方法 allocateDirect（）可以创建直接字节缓冲区
 * ---------------------------------------------------------------------------
 * |           进行内存的分配和释放所需的时间成本通常要高于非直接缓冲区            |                              |
 * |           因为直接缓冲区的操作不在jvm堆中，而是在内核空间                    |
 * |           从这点可以得出这样的数据结构是专门针对i/o操作量大，耗时久的工作     |
 * |           释放内存方式有两种：1、手动释放（调用机器的方法）2、交给jvm处理     |
 * ---------------------------------------------------------------------------
 * <p>
 * 非直接缓冲区------
 * <p>
 * <p>
 * ---------------------------------------------------------------------------
 * |           作用于jvm的堆内存，由jvm管理，通过数组来实现缓冲数据的存放            |                              |
 * ---------------------------------------------------------------------------
 *
 * @author 徐健
 * @version 1.0.0
 * @ClassName BufferDemo3.java
 * @Description TODO
 * @createTime 2019年08月20日 08:27:00
 */
public class BufferDemo3 {

    @Test
    public void test1() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{'A', 'B', 'C'});

        byte[] dst = new byte[byteBuffer.capacity()];
//        byte[] dst = new byte[4];//java.nio.BufferUnderflowException

        //通过目的数组 连续读取缓冲区内容
        byteBuffer.get(dst);
        for (byte b : dst) {
            System.out.println((char) b);
        }

        //恢复参数
        byteBuffer.clear();

        byte[] src = new byte[]{'D', 'E', 'F'};
        //连续读取数组中内容传输到缓冲区存储
        byteBuffer.put(src);//<=remaining()否则会抛出异常BufferUnderflowException

        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }


    @Test
    public void test2() throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        byte[] bytes = {1};//存入一个字节
        for (int i = 0; i <Integer.MAX_VALUE ; i++) {
            byteBuffer.put(bytes);
        }
        System.out.println("put done!");
        Thread.sleep(1000);


        System.out.println("下面是手动释放内存的方式----------");
        Method cleaner = byteBuffer.getClass().getMethod("cleaner");
        cleaner.setAccessible(true);
        Object returnValue = cleaner.invoke(byteBuffer);//返回cleaner
        Method clean = returnValue.getClass().getMethod("clean");
        clean.setAccessible(true);
        clean.invoke(returnValue);//通过返回的cleaner调用clean方法

        //通过任务管理器的性能标签页中的内存消耗：从8.5g到6.5g差不多占了2个g内存
        //占的内存量可以由计算得出：Integer.MAX_VALUE=2^31
        //2^31=2^11*2^10*2^10=2048*1024*1024
        //也就是2048兆


    }
}
