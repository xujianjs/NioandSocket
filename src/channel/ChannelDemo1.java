package channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName ChannelDemo1.java
 * @Description --------------------------------------------------------------------------------------------
 * 在nio技术中，需要将数据打包到缓冲区中，而缓冲区中的数据想要传输到目的地，需要借助于通道
 * 缓冲区用来打包数据，通道用来传输，两者形影不离
 * <p>
 * <p>
 * <p>
 * |------------- |              |----------------------------------------|              |-------------|
 * |              |              |                                        |              |             |
 * |    源缓冲区  |     =>       |                   数据                 |      =>     |   目的缓冲区 |
 * |             |              |                                        |              |              |
 * |-------------|              |----------------------------------------|              |-------------|
 * <p>
 * 通道
 * <p>
 * <p>
 * --------------------------------------------------------------------------------------------
 * @createTime 2019年08月23日 15:11:00
 */
public class ChannelDemo1 {
    @Test
    public void test1() throws IOException {
        //创建输出到磁盘文件的    文件输出流
        FileOutputStream fileOutputStream =
                new FileOutputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\demo.txt"));

        //获取文件通道
        FileChannel channel = fileOutputStream.getChannel();
        //包装缓冲区数据
        ByteBuffer buffer1 = ByteBuffer.wrap("abcde".getBytes());
        //将缓冲区数据写入到通道中
        channel.write(buffer1);
        //关闭通道
        channel.close();
        //关闭输出流
        fileOutputStream.close();

    }


    @Test
    public void test2() throws IOException {
        //创建输出到磁盘文件的    文件输出流
        FileOutputStream fileOutputStream =
                new FileOutputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\demo.txt"));

        //获取文件通道
        FileChannel channel = fileOutputStream.getChannel();
        //包装缓冲区数据
        ByteBuffer buffer1 = ByteBuffer.wrap("abcde".getBytes());
        ByteBuffer buffer2 = ByteBuffer.wrap("12345".getBytes());
        //将缓冲区数据写入到通道中
        //验证通道是从position位置开始写入的

        System.out.println("channel.position()=" + channel.position());
        channel.write(buffer1);
        channel.position(2);
        channel.write(buffer2);//ab12345
        //关闭通道
        channel.close();
        //关闭输出流
        fileOutputStream.close();

    }

    @Test
    public void test3() throws IOException {
        //创建输出到磁盘文件的    文件输出流
        FileOutputStream fileOutputStream =
                new FileOutputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\demo.txt"));

        //获取文件通道
        FileChannel channel = fileOutputStream.getChannel();

        //将缓冲区数据写入到通道中
        //验证write()方法具有同步特性 多个缓冲区同时写入  不会出现写入内容之间交叉的情况  如ab123cde45等

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(() -> {
                //包装缓冲区数据
                ByteBuffer buffer1 = ByteBuffer.wrap("abcde\n".getBytes());
                ByteBuffer buffer2 = ByteBuffer.wrap("12345\n".getBytes());

                try {

                    channel.write(buffer1);
                    channel.write(buffer2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "channel.position()=" + channel.position();

            });
        }
        ;
        try {
            List<Future<String>> futures = executorService.invokeAll(tasks);
            futures.forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //关闭通道
        channel.close();
        //关闭输出流
        fileOutputStream.close();

    }

    /*
     * 创建FileChannel需要区分FileOutputStream和FileInputStream,
     * 如果是用FileOutputStream创建的Channel只能进行写操作而不能惊醒读操作，
     * 否则会报NonReadableChannelException,反之用FileInputStream
     * 创建的Channel只能进行读操作，而不能进行写操作，否则会报NonWritableChannelException
     * 当然除此之外，可以使用RandomAccessFile来替换，但必须指定操作方式，就像
     * mode = "r" / "rw" 但不可以是 "w"
     * RandomAccessFile r = new RandomAccessFile(file, mode)
     */

    @Test
    public void test4() throws IOException {


        //创建输出到磁盘文件的    文件输入流
        FileInputStream fileInputStream =
                new FileInputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\demo.txt"));
//
//        //获取文件通道
        FileChannel channel = fileInputStream.getChannel();

        //读取文件通道中内容 输出到目标缓冲区
        ByteBuffer dsts = ByteBuffer.allocate(100);
        channel.position(3);//验证read(ByteBuffer dst)方法是从position位置开始读取的
        dsts.position(2);//验证read(ByteBuffer dst)方法是将字节放入到缓冲区的position位置开始的
        channel.read(dsts);
        byte[] array = dsts.array();
        for (byte b : array) {
            System.out.println((char) b);
        }
    }

    @Test
    public void test5() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\b.txt"));
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer allocate1 = ByteBuffer.allocate(2);
        ByteBuffer allocate2 = ByteBuffer.allocate(2);
        ByteBuffer[] byteBuffers = {allocate1, allocate2};

        long readLength = channel.read(byteBuffers, 0, 2);
        allocate1.clear();
        allocate2.clear();
        System.out.println(readLength);
        readLength = channel.read(byteBuffers, 0, 2);
        allocate1.clear();
        allocate2.clear();
        System.out.println(readLength);
        readLength = channel.read(byteBuffers, 0, 2);
        allocate1.clear();
        allocate2.clear();
        System.out.println(readLength);

        channel.close();
        fileInputStream.close();

    }

    @Test
    public void test6() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\study\\gitProjects\\Nio and Socket\\b.txt"));
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer allocate1 = ByteBuffer.allocate(8);
        ByteBuffer allocate2 = ByteBuffer.allocate(8);
        //验证read(ByteBuffer[] dsts, int offset, int length)方法将字节放入ByteBuffer当前位置
        allocate1.position(2);
        allocate2.position(3);

        ByteBuffer[] byteBuffers = {allocate1, allocate2};

        //把内容读取到    由多个缓冲区组成的数组中 ByteBuffer[]
        //验证得出  虽然读取内容是连续的  但是都是从每个缓冲区中的position读取的  可能在地址空间就不连续  数据会零散的存在不同的缓冲区中  （依然是有序的哦）
        channel.read(byteBuffers, 0, 2);

        byte[] array1 = allocate1.array();
        byte[] array2 = allocate2.array();

        for (byte b : array1) {
            System.out.println((char)b);
        }

        for (byte b : array2) {
            System.out.println((char)b);
        }

        channel.close();
        fileInputStream.close();

    }


}
