package polymorphism;

/**
 * @author 徐健
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2019年08月16日 13:07:00
 */
public class Test {
    public static void main(String[] args) {
        Animal cat = new Cat();
        Dog dog = new Dog();
        System.out.println(cat instanceof Animal);
        System.out.println(cat instanceof Cat);
        Animal animal = new Animal();
//        cat.say();//Error:(7, 12) java: say() 在 polymorphism.Animal 中是 private 访问控制
//        animal.say();//Error:(9, 15) java: say() 在 polymorphism.Animal 中是 private 访问控制
        cat.walk();
//        cat.say();//这样也是不行的 因为先实例化父类 再实例化子类 所以父类引用的化  也是无法在外部访问私有方法的（父类的say是私有方法）
        ((Cat)cat).say();//可以把cat强转为子类Cat 这样就能在外部访问了（因为子类的方法相当于是新的方法 注意这里是不能重写父类私有方法的  这里的say相当于是一个新方法）
        dog.walk();
        dog.say();//除了通过强转成子类 调用子类方法  还可以通过直接引用指向子类Dog  直接调用子类的公开方法say

    }
}
