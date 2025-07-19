1. Наследование с функциональной вариацией - без переопределения сигнатуры.
2. Наследование с конкретизацией.
3. Cтруктурное наследование - добавляем некоторое качественно новое свойство, семантически не привязанное к родительскому классу:
```java
interface Printable {
  void print();
}

// Когда наследовать имеет смысл именно абстрактное свойство - здесь обоим классам нужно *что-то* печатать,
// но это *что-то* в каждом случае принципиально своё

class TextDocument implements Printable {
  private String documentContent;

  public TextDocument(String contents) {
    this.documentContent = contents;
  }

  @Override
  public void print() {
    System.out.println("Document contents are: " + documentContent);
  }
}

class Image implements Printable {
  private String fileName;

  public Image(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void print() {
    System.out.println("This is the image named " + fileName);
  }
}
```
