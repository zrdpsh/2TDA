// базовый класс 
class Logger {
    private final String component;

    public Logger(String component) {
        this.component = component;
    }
// базовый метода для вывода лога
    public void log(String level, String message) {
        System.out.println("[" + level + "] " + component + ": " + message);
    }
    protected String getComponent() {
        return component;
    }
}

// расширение - метод лог выводит больше информации
class DetailedLogger extends Logger {
    public DetailedLogger(String component) {
        super(component);
    }
// здесь, кроме уровня логирования и сообщения, прописывается время и дата, для этого нужно переписывать весь вызов метода
    @Override
    public void log(String level, String message) {
        String timestamp = java.time.LocalDateTime.now().toString();
        System.out.println("[" + timestamp + "] [" + level + "] "
                + getComponent() + ": " + message);
    }
}

// специализация - с помощью этого метода логируются только ошибки
class ErrorLogger extends Logger {
    public ErrorLogger(String component) {
        super(component);
    }

// здесь переписанный метод будет выводить только сообщения об ошибках
    @Override
    public void log(String level, String message) {
        if (!"ERROR".equals(level)) {
            return;
        }
        super.log(level, message);
    }
}

