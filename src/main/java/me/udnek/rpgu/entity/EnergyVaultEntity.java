package me.udnek.rpgu.entity;

// TODO: 7/26/2024 FIX

/*public class EnergyVaultEntity extends ConstructableCustomEntity {
    public static final NamespacedKey ENERGY_NAMESPACED_KEY = new NamespacedKey(RpgU.getInstance(), "vault_energy");
    public static final float MAX_ENERGY = 4096f;

    public float getEnergy(Entity entity){
        return entity.getPersistentDataContainer().get(ENERGY_NAMESPACED_KEY, PersistentDataType.FLOAT);
    }

    protected void setEnergy(Entity entity, float amount){
*//*        if (amount > MAX_ENERGY) amount = MAX_ENERGY;
        else if (amount < 0) amount = 0;*//*
        entity.getPersistentDataContainer().set(ENERGY_NAMESPACED_KEY, PersistentDataType.FLOAT, amount);
    }

    public float addEnergy(Entity entity, float amount){
        float currentEnergy = getEnergy(entity);
        float finalEnergy = currentEnergy + amount;
        if (finalEnergy > MAX_ENERGY) finalEnergy = MAX_ENERGY;
        else if (finalEnergy < 0) finalEnergy = 0;
        setEnergy(entity, finalEnergy);
        return finalEnergy-currentEnergy;
    }

    @Override
    protected void extraAdjustAfterSpawn(Entity entity) {
        ItemDisplay display = (ItemDisplay) entity;
        setEnergy(entity, 0);

        ItemStack itemStack = new ItemStack(Material.FEATHER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(3000);
        itemStack.setItemMeta(itemMeta);
        display.setItemStack(itemStack);

        Transformation transformation = display.getTransformation();
        transformation.getScale().set(3.001);
        display.setTransformation(transformation);
        display.setRotation(0, 0);

        display.teleport(entity.getLocation().toCenterLocation());

        Location location = entity.getLocation();
        TextDisplay textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        entity.addPassenger(textDisplay);
        Transformation textTransformation = textDisplay.getTransformation();
        textTransformation.getTranslation().set(-0.01, 0.85,1.5001);
        textDisplay.setTransformation(textTransformation);
        textDisplay.setBrightness(new Display.Brightness(15, 15));
        textDisplay.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        textDisplay.setRotation(
                blockFaceToRotation(((Directional) (location.getBlock().getBlockData()) ).getFacing()), 0
        );
        updateEnergyText(entity);
    }

    public void destroy(Entity entity){
        for (Entity passenger : entity.getPassengers()) {
            passenger.remove();
        }
        entity.remove();
    }

    public void updateEnergyText(Entity entity){
        TextDisplay textDisplay = (TextDisplay) entity.getPassengers().get(0);
        textDisplay.text(generateEnergyText(getEnergy(entity)));
    }

    public Component generateEnergyText(float amount){
        StringBuilder amountStr = new StringBuilder();
        int intAmount = (int) Math.ceil(amount);
        if (intAmount == 0) amountStr.append("0".repeat(3));
        else amountStr.append("0".repeat((int) (4 - Math.log10(intAmount+1))));
        amountStr.append(intAmount).append("/"+ (int) Math.ceil(MAX_ENERGY));
       return Component.text(amountStr.toString()).color(TextColor.color(1f, 0, 0)).font(Key.key("rpgu:energy"));
    }



    @Override
    public void tick(Entity entity) {
        if (getFixedCurrentTick() % 5 != 0) return;
        Location location = entity.getLocation();

        if (!Structures.energyVault.isStandingHere(location)) {
            location.getWorld().createExplosion(location, 3, false, false);
            destroy(entity);
            return;
        }

        addEnergy(entity, 7f);
        LogUtils.log(String.valueOf(getEnergy(entity)));


        int power = location.getBlock().getBlockPower();
        if (power > 0) {
            float energy = -addEnergy(entity, -20f);
            if (energy > 0) ElectricCharge.createChargeAtCore(location, energy);

        }

        updateEnergyText(entity);

    }

    public float blockFaceToRotation(BlockFace blockFace){
        switch (blockFace){
            case NORTH:
                return 180;
            case WEST:
                return 90;
            case SOUTH:
                return 0;
            case EAST:
                return -90;
        }
        return 0;
    }

    @Override
    protected String getName() {
        return "energy_vault";
    }
    @Override
    public void onLoad(Entity entity) {
        LogUtils.log("LOAD");
    }
    @Override
    public void onUnload(Entity entity) {
        LogUtils.log("UNLOAD");
    }
    @Override
    public EntityType getEntityType() {
        return EntityType.ITEM_DISPLAY;
    }
}
*/