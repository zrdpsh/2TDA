1. Используем тот же пример с классами-разборщиками логов:
```java
public interface LogParser {
  void parseLine(String line);
}

public abstract class BaseLogParser implements LogParser {
  private final LineSink sink;
  protected BaseLogParser(LineSink sink) { this.sink = sink; }
  protected void emit(String msg, String ip) { sink.accept(new ParsedLine(msg, ip)); }
}

// реализация одного "обычного" класса в иерархии
public class ApacheLogParser extends BaseLogParser {
  public ApacheLogParser(LineSink sink) { super(sink); }
  @Override
  public void parseLine(String line) {
    String[] parts = line.split(" ");
    if (parts.length > 6) {
      emit(parts[6], parts[0]);
    }
  }
}
```

2. Теперь добавляем NoneParser
```java
public final class NoneParser extends BaseLogParser {
  public static final NoneParser INSTANCE = new NonParser();

  private NoneParser() {
    // sink нужно переопределить, чтобы не возникло null
    super(line -> {});
  }

  @Override
  public void parseLine(String line) {
    // теперь метод ничего не делает
  }
}
```

3. И теперь - в обоих вызовах parseLine выполнение остаётся внутри иерархии Parser, независимо от того, что попадается на входе:
```java
public class LogApp {
  public static void main(String[] args) {
    // получаем "откуда-то" флажок
    boolean isParsing = false;
    LogParser parser = isParsing ? new ApacheLogParser(new StdoutSink()) : NoneParser.INSTANCE;
    
    parser.parseLine("192 001 001 001");
    parser.parseLine(" ");
  }
}
```
