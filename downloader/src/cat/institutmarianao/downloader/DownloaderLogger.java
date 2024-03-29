package cat.institutmarianao.downloader;

import java.text.MessageFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class DownloaderLogger {
	private DownloaderLogger() {
	}

	public static void setup(Logger logger) {
		ConsoleHandler consoleHandler = new ConsoleHandler();
		Formatter formatter = new SimpleFormatter() {
			private static final String FORMAT = "[%1$-7s] %2$s %n";

			@Override
			public synchronized String format(LogRecord logRecord) {
				return String.format(FORMAT, logRecord.getLevel().getLocalizedName(),
						MessageFormat.format(logRecord.getMessage(), logRecord.getParameters()));
			}
		};
		logger.setUseParentHandlers(false);
		consoleHandler.setFormatter(formatter);
		logger.addHandler(consoleHandler);
	}
}
