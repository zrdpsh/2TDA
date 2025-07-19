// 2. Наследование с конкретизацией.
abstract class Shape {
  public abstract double area();
}

class Circle extends Shape {
  private double radius;

  public Circle(double radius) {
    this.radius = radius;
  }

  @Override
  public double area() {
    return Math.PI * radius * radius;
  }
}

class Rectrangle extends Shape {
  private double width, height;

  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
}

  @Override
  public double area() {
    return widht * height;
  }
}
