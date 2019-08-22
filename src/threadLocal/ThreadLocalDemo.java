package threadLocal;

import org.junit.Test;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName ThreadLocalDemo.java
 * @Description TODO
 * @createTime 2019年08月21日 13:59:00
 */
public class ThreadLocalDemo {
    static ThreadLocal threadLocal1 = new ThreadLocal();
    static ThreadLocal threadLocal2 = new ThreadLocal();

    static class BindValue {
        public  void setFirst(String value) {

            threadLocal1.set(value.hashCode());
            long id = Thread.currentThread().getId();
            System.out.println("线程:"+id+"---setFirst---"+threadLocal1.get() + " : " + threadLocal2.get());

        }

        public  void setSecond(String value) {
            threadLocal2.set(value.hashCode());
            long id = Thread.currentThread().getId();
            System.out.println("线程:"+id+"---setSecond---"+threadLocal1.get() + " : " + threadLocal2.get());
        }

    }

    static class ShowValue {
        public  void show() {
            long id = Thread.currentThread().getId();
            System.out.println("线程:"+id+threadLocal1.get() + " : " + threadLocal2.get());
        }
    }

    @Test
    public void test1() {

        BindValue bindValue = new BindValue();
        ShowValue showValue = new ShowValue();

        for (int i = 0; i < 20; i++) {
            int finalI = i;
            Runnable r = () -> {

                bindValue.setFirst(String.valueOf(finalI));
                bindValue.setSecond(String.valueOf(finalI));
                showValue.show();
            };
            new Thread(r).start();

        }
    }
}
