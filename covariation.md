1. Ковариантность возвращаемых типов.

Имеем:
Интерфейс парсера
```java
interface LogParser {
  void parseLine(String line);
}
```
Абстрактный класс, реализующий интерфейс LogParser
```java
abstract class BaseLogParser implements LogParser {
    private final LineSink sink;
    protected BaseLogParser(LineSink sink) { this.sink = sink; }
    protected void emit(String msg, String ip) { sink.accept(new ParsedLine(msg, ip)); }
}
```
Абстрактную фабрику, использующую внутри абстрактный класс BaseLogParser
```java
abstract class ParserFactory {
    public abstract BaseLogParser create(LineSink sink);
}
```
Конкретную реализацию парсера
```java
class ApacheLogParser extends BaseLogParser {
    public ApacheLogParser(LineSink sink) { super(sink); }
    @Override public void parseLine(String line) { /* … */ }
}
```
Конкретную реализацию фабрики, использующую конкретную реализацию парсера
```java
class ApacheParserFactory extends ParserFactory {
    @Override
    public ApacheLogParser create(LineSink sink) {
        return new ApacheLogParser(sink);
    }
}
```

```java
```

```java
```

```java
```

```java
```

```java
```
