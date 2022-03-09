package FactoryPattern;

/**
 * @author yujiahua
 */
public class FactoryPattern {
    public static void main(String[] args) {
        ShapeFactory sf = new ShapeFactory();
        Shape shape1 = sf.getShape("circle");
        shape1.draw();;
        Shape shape2 = sf.getShape("rectangle");
        shape2.draw();
        Shape shape3 = sf.getShape("square");
        shape3.draw();
    }
}
