package me.udnek.rpgu.item;

import me.udnek.itemscoreu.customattribute.equipmentslot.CustomEquipmentSlot;
import me.udnek.itemscoreu.customevent.AllEventListener;
import me.udnek.itemscoreu.customitem.ConstructableCustomItem;
import me.udnek.itemscoreu.utils.ComponentU;
import me.udnek.itemscoreu.utils.LogUtils;
import me.udnek.itemscoreu.utils.RightClickable;
import me.udnek.rpgu.RpgU;
import me.udnek.rpgu.item.abstraction.ArtifactItem;
import me.udnek.rpgu.util.CooldownData;
import me.udnek.rpgu.util.PerPlayerData;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;

public class CopperSword extends ConstructableCustomItem implements RightClickable, ArtifactItem, AllEventListener {

    private final CooldownData cooldownData = new CooldownData(10);
    private final WindupData windupData = new WindupData();

    @Override
    public Integer getCustomModelData() {
        return 3100;
    }
    @Override
    public Material getMaterial() {
        return Material.GOLDEN_SWORD;
    }
    @Override
    public String getRawId() {
        return "copper_sword";
    }

    @Override
    public void onRightClicks(PlayerInteractEvent event) {
        ItemStack eventItem = event.getItem();
        ItemMeta itemMeta = eventItem.getItemMeta();
        itemMeta.setCustomModelData(itemMeta.getCustomModelData() == 3100 ? 3101 : 3100);
        eventItem.setItemMeta(itemMeta);
        event.getPlayer().getInventory().setItem(event.getHand(), eventItem);
    }

    @Override
    protected List<Recipe> generateRecipes() {
        ShapedRecipe recipe = new ShapedRecipe(this.getRecipeNamespace(0), this.getItem());
        recipe.shape(
                " C ",
                " C ",
                " S ");

        recipe.setIngredient('C', new RecipeChoice.MaterialChoice(Material.COPPER_INGOT));
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(Material.STICK));

        return Collections.singletonList(recipe);
    }

    @Override
    public void onBukkitEvent(Event unknowEvent) {
        if (!(unknowEvent instanceof EntityShootBowEvent event)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        Material material = event.getBow().getType();
        if (material != Material.CROSSBOW) return;
        Integer stacks = windupData.get(player);
        if (stacks == 0) return;

        LogUtils.log("PROCEED");

        windupData.takeStack(player);
        cooldownData.set(player);
        player.setCooldown(Material.CROSSBOW, 15);

        new BukkitRunnable() {
            @Override
            public void run() {
                LogUtils.log("PROCEED 2");

                ItemStack crossbow = player.getInventory().getItem(event.getHand());
                CrossbowMeta itemMeta = (CrossbowMeta) crossbow.getItemMeta();
                itemMeta.setChargedProjectiles(Collections.singletonList(event.getConsumable()));
                crossbow.setItemMeta(itemMeta);
                player.getInventory().setItem(event.getHand(), crossbow);
            }
        }.runTaskLater(RpgU.getInstance(), 1);
    }

    @Override
    public List<Component> getHudImages(Player player) {
        return Collections.singletonList(
                windupData.getImage(player).append(cooldownData.getImage(player))
        );
    }

    @Override
    public void onEquipped(Player player, CustomEquipmentSlot slot, ItemStack itemStack) {
        windupData.remove(player);
        cooldownData.set(player);
    }

    @Override
    public void tickBeingEquipped(Player player, CustomEquipmentSlot slot) {
        if (windupData.get(player) >= 5) return;
        if (cooldownData.has(player)) return;
        windupData.addStack(player);
        if (windupData.get(player) < 5) cooldownData.set(player);
    }

    private class WindupData extends PerPlayerData<Integer>{

        @Override
        public Integer get(Player player) {
            Integer stacks = data.get(player);
            if (stacks == null) return 0;
            return stacks;
        }

        public void addStack(Player player){
            Integer stacks = get(player);
            stacks++;
            data.put(player, stacks);
        }
        public void takeStack(Player player){
            Integer stacks = get(player);
            stacks--;
            if (stacks <= 0) data.remove(player);
            else data.put(player, stacks);
        }

        public Component getImage(Player player){
            return ComponentU.textWithNoSpace(Component.translatable("item.rpgu.copper_sword.image." + get(player)), 16);
        }
    }

}




















