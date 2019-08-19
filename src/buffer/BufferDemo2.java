package buffer;

import java.nio.ByteBuffer;
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

}
