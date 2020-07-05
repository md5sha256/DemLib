package dev.demeng.demlib.api.messages;

import dev.demeng.demlib.DemLib;
import dev.demeng.demlib.api.Common;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/** Basic messaging-related utilities. */
public final class MessageUtils {

  @Getter @Setter private static String prefix;

  public static final String CONSOLE_LINE =
      "!-----------------------------------------------------!";
  public static final String CHAT_LINE =
      ChatColor.STRIKETHROUGH + "-----------------------------------------------------";

  /***
   * Returns a colored messages with line breaks.
   * @param messages
   * @return
   */
  public static String colorize(String... messages) {
    return ChatColor.translateAlternateColorCodes('&', StringUtils.join(messages, "\n"));
  }

  /**
   * Returns a colored list.
   *
   * @param list
   * @return
   */
  public static List<String> colorize(List<String> list) {
    final List<String> copy = new ArrayList<>();
    for (String s : list) copy.add(colorize(s));
    return copy;
  }

  /**
   * Returns a colored and prefixed string.
   *
   * @param s
   * @return
   */
  public static String format(String s) {
    return colorize(prefix + s);
  }

  /**
   * Send a colored and prefixed message to the console.
   *
   * @param messages
   */
  public static void console(String... messages) {
    for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(format(msg));
  }

  /**
   * Send a message to the console without a prefix.
   *
   * @param messages
   */
  public static void consoleClean(String... messages) {
    for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(colorize(msg));
  }

  /**
   * Log a simple INFO message to the console.
   *
   * @param message
   */
  public static void log(String message) {
    DemLib.getPlugin().getLogger().info(message);
  }

  /**
   * Log information to the console.
   *
   * @param level The level of the log
   * @param message
   */
  public static void log(Level level, String message) {
    DemLib.getPlugin().getLogger().log(level, message);
  }

  /**
   * Send a colored and prefixed message.
   *
   * @param sender
   * @param messages
   */
  public static void tell(CommandSender sender, String... messages) {
    for (final String msg : messages) sender.sendMessage(format(msg));
  }

  /**
   * Send a colored and prefixed message.
   *
   * @param player
   * @param messages
   */
  public static void tell(Player player, String... messages) {
    for (final String msg : messages) player.sendMessage(format(msg));
  }

  /**
   * Send a message without a prefix.
   *
   * @param sender
   * @param messages
   */
  public static void tellClean(CommandSender sender, String... messages) {
    for (final String msg : messages) sender.sendMessage(colorize(msg));
  }

  /**
   * Send a message without a prefix.
   *
   * @param player
   * @param messages
   */
  public static void tellClean(Player player, String... messages) {
    for (final String msg : messages) player.sendMessage(colorize(msg));
  }

  /**
   * Broadcast a message.
   *
   * @param messages
   */
  public static void broadcast(String... messages) {
    for (final String msg : messages) Bukkit.broadcastMessage(format(msg));
  }

  /**
   * Broadcast a message without a prefix.
   *
   * @param messages
   */
  public static void broadcastClean(String... messages) {
    for (final String msg : messages) Bukkit.broadcastMessage(colorize(msg));
  }

  /**
   * Send a fancy error report to the console.
   *
   * @param ex The exception that occurred, setting it to null will print no stack traces
   * @param id The ID of the error
   * @param description The description of the error
   * @param disable Should the plugin be disabled
   */
  public static void error(Throwable ex, int id, @NonNull String description, boolean disable) {

    if (ex != null) {
      log(Level.WARNING, "Error! Generating stack trace...");
      ex.printStackTrace();
    }

    console(
        "&4" + CONSOLE_LINE,
        "&cUnfortunately, an error has occurred in " + Common.getName() + ".",
        "&cIf you are unable to fix this issue, please contact support.",
        "&cBelow are some important information regarding the error.",
        "&cError Identification Number: &6" + id,
        "&cDescription: &6" + description,
        "&4" + CONSOLE_LINE);

    if (disable) Bukkit.getPluginManager().disablePlugin(DemLib.getPlugin());
  }

  /**
   * Colorize, and then strip the message.
   *
   * @param message The message to color and strip
   * @return
   */
  public static String colorAndStrip(String message) {
    return ChatColor.stripColor(colorize(message));
  }

  /**
   * Replaces color code sign with &.
   *
   * @param message The message to revert colorizing
   * @return
   */
  public static String revertColorizing(String message) {
    return message.replaceAll("(?i)" + ChatColor.COLOR_CHAR + "([0-9a-fk-or])", "&$1");
  }

  /**
   * Turns the colored message into a raw message.
   *
   * @param message The message to strip
   * @return
   */
  public static String stripColors(String message) {
    return message == null
        ? ""
        : message.replaceAll("(" + ChatColor.COLOR_CHAR + "[§&])([0-9a-fk-or])", "");
  }
}
