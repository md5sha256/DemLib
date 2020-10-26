package dev.demeng.demlib.menu;

import dev.demeng.demlib.item.ItemCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/** A menu button. */
@AllArgsConstructor
public class MenuButton {

  /** The slot that the button will be displayed in. */
  @Getter @Setter public int slot;

  /** The ItemStack of the button. */
  @Getter @Setter public ItemStack stack;

  /** The actions executed when the button is clicked. */
  @Getter @Setter public Consumer<InventoryClickEvent> actions;

  public void run(InventoryClickEvent e) {
    actions.accept(e);
  }

  /**
   * Retrieve a button from a configuration file.
   *
   * @param config The configuration file to look in
   * @param path The path to the button configuration
   * @return A CustomButton without any actions
   */
  public static MenuButton fromConfig(FileConfiguration config, String path) {

    final int slot = config.getInt(path + ".slot") - 1;

    final ItemStack stack =
        ItemCreator.quickBuild(
            ItemCreator.getMaterial(config.getString(path + ".material")),
                config.getString(path + ".display-name"),
                config.getStringList(path + ".lore"));

    return new MenuButton(slot, stack, null);
  }
}
