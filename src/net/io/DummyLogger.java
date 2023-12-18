package net.io;

import org.eclipse.jetty.util.log.Logger;

public class DummyLogger implements Logger {

    public String getName() {
        return "DummyLogger";
    }

    public void warn(String msg, Object... args) {}

    public void warn(Throwable thrown) {}

    public void warn(String msg, Throwable thrown) {}

    public void info(String msg, Object... args) {}

    public void info(Throwable thrown) {}

    public void info(String msg, Throwable thrown) {}

    public boolean isDebugEnabled() {return false; }

    public void setDebugEnabled(boolean enabled) {}

    public void debug(String msg, Object... args) {}

    public void debug(Throwable thrown) {}

    public void debug(String msg, Throwable thrown) {}

    public Logger getLogger(String name) {return this; }

    public void ignore(Throwable ignored) {}

	public void debug(String msg, long value) {}

}