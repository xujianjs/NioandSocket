package buffer;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName BufferDemo5.java
 * @Description TODO
 * @createTime 2019年08月23日 14:48:00
 */
public class BufferDemo5 {
    @Test
    public void test1() {
        CharBuffer charBuffer = CharBuffer.allocate(10);
        charBuffer.append("abcdefg");

        charBuffer.position(2);

        //charAt(relativeIndex)
        //charAt方法是  读取相对于当前位置给定索引处的值
        System.out.println(charBuffer.charAt(0));//c
        System.out.println(charBuffer.charAt(1));//d
        System.out.println(charBuffer.charAt(2));//e
    }

    @Test
    public void test2() throws IOException {
        CharBuffer charBuffer1 = CharBuffer.allocate(8);
        charBuffer1.append("ab123456");
        charBuffer1.position(2);
        charBuffer1.put("cde");
        charBuffer1.rewind();

        for (int i = 0; i < charBuffer1.limit(); i++) {
            System.out.print(charBuffer1.get());
        }

        charBuffer1.position(1);

        CharBuffer charBuffer2 = CharBuffer.allocate(4);
        charBuffer1.read(charBuffer2);//相当于从位置1的地方开始导出
        charBuffer2.rewind();
        System.out.println();
        for (int i = 0; i < charBuffer2.limit(); i++) {
            System.out.print(charBuffer2.get());
        }

        charBuffer1.position(2);
        CharBuffer charBuffer3 = charBuffer1.subSequence(0, 2);
        System.out.println();
        for (int i = charBuffer3.position(); i < charBuffer3.limit(); i++) {
            System.out.print(charBuffer3.get());
        }


    }
}
