package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Loggable {
    final Logger LOGGER = LogManager.getLogger();
}
