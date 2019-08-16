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
    LongBuffer longBuffer = LongBuffer.wrap(longArray);
    FloatBuffer floatBuffer = FloatBuffer.wrap(floatArray);
    DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubleArray);
    CharBuffer charBuffer = CharBuffer.wrap(charArray);

    System.out.printf("byteBuffer:%s,byteBuffer.capacity=%s\n", byteBuffer.getClass().getName(),
        byteBuffer.capacity());
    System.out.printf("shortBuffer:%s,shortBuffer.capacity=%s\n", shortBuffer.getClass().getName(),
        shortBuffer.capacity());
    System.out.printf("intBuffer:%s,intBuffer.capacity=%s\n", intBuffer.getClass().getName(),
        intBuffer.capacity());
    System.out.printf("longBuffer:%s,longBuffer.capacity=%s\n", longBuffer.getClass().getName(),
        longBuffer.capacity());
    System.out.printf("floatBuffer:%s,floatBuffer.capacity=%s\n", floatBuffer.getClass().getName(),
        floatBuffer.capacity());
    System.out
        .printf("doubleBuffer:%s,doubleBuffer.capacity=%s\n", doubleBuffer.getClass().getName(),
            doubleBuffer.capacity());
    System.out.printf("charBuffer:%s,charBuffer.capacity=%s\n", charBuffer.getClass().getName(),
        charBuffer.capacity());
  }

  @Test
  public void test3() {
    char[] charArray = new char[]{'a', 'b', 'c', 'd'};
    CharBuffer charBuffer = CharBuffer.wrap(charArray);
    //capacity容量    limit第一个不能开始读写的位置   position下一个开始读写的位置  remaining返回position和limit之间的元素个数

    System.out.printf(
        "charBuffer:%s ,charBuffer.capacity=%s ,charBuffer.limit=%s ,charBuffer.position=%s\n",
        charBuffer.getClass().getName(), charBuffer.capacity(), charBuffer.limit(),
        charBuffer.position());
    System.out.println(charBuffer);//abcd
    System.out.printf("charBuffer.remaining=%s\n", charBuffer.remaining());
    charBuffer.position(2);
    System.out.printf(
        "charBuffer:%s ,charBuffer.capacity=%s ,charBuffer.limit=%s ,charBuffer.position=%s\n",
        charBuffer.getClass().getName(), charBuffer.capacity(), charBuffer.limit(),
        charBuffer.position());
    System.out.println(charBuffer);//cd
    System.out.printf("charBuffer.remaining=%s\n", charBuffer.remaining());//4-2=2

  }

  @Test
  public void test4() {
    char[] charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
    CharBuffer charBuffer = CharBuffer.wrap(charArray);
    System.out.printf("capacity容量:%s\n", charBuffer.capacity());

    //mark是作为标记 同步当前的position 即使读写改变了position  调用reset也能恢复到mark的索引处（类似于登山标记/探险路标）

    charBuffer.position(0);
    charBuffer.mark();//在位置0处设置mark
    System.out.printf("position下一个开始读写的位置:%s\n", charBuffer.position());
    System.out.println(charBuffer);//abcde

    charBuffer.put(4, 't');//改变下表第4的字符
//        charBuffer.put(5, 'z');//java.lang.IndexOutOfBoundsException
    charBuffer.position(3);//改变位置
    System.out.printf("position下一个开始读写的位置:%s\n", charBuffer.position());
    System.out.println(charBuffer);//dt

    charBuffer.reset();//如果不定义mark  将抛出java.nio.InvalidMarkException
    System.out.printf("position下一个开始读写的位置:%s\n", charBuffer.position());
    System.out.println(charBuffer);//abcdt
  }

  @Test
  public void test5() {
    char[] charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
    CharBuffer charBuffer = CharBuffer.wrap(charArray);

    charBuffer.position(3);
    charBuffer.mark();

//        charBuffer.position(1);//如果position<mark 那么将会丢弃mark  抛出java.nio.InvalidMarkException
    charBuffer.position(4);
    charBuffer.reset();
    System.out.println(charBuffer);

  }

  @Test
  public void test6() {
    char[] charArray = new char[]{'a', 'b', 'c', 'd', 'e'};
    CharBuffer charBuffer = CharBuffer.wrap(charArray);

    charBuffer.limit(3);
    charBuffer.position(3);
    charBuffer.put(3,
        't');//java.lang.IndexOutOfBoundsException  因为limit是第一个不可读写的索引    而position又是指下一个要读写的索引  两者重合就是矛盾的

    System.out.println(charBuffer);

  }


}
