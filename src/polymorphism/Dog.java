package polymorphism;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName polymorphism.Dog.java
 * @Description TODO
 * @createTime 2019年08月16日 12:52:00
 */
public class Dog extends Animal {
    void say() {
        System.out.println("狗叫。。。");
    }

    //Error:(13, 10) java: Dog中的walk()无法覆盖Animal中的walk()
    //  正在尝试分配更低的访问权限; 以前为public
//    void walk() {
//
//    }
}
