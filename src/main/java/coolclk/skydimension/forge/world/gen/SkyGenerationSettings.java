package coolclk.skydimension.forge.world.gen;

import net.minecraft.world.gen.GenerationSettings;

public class SkyGenerationSettings extends GenerationSettings {
    protected int floatingShipCityDistance = 20;
    protected final int floatingShipSeparation = 11;

    /**
     * @return The distance between each Floating Ship structure <i>(Unit: chunk)</i>
     * @author CoolCLK
     */
    public int getFloatingShipDistance() {
        return this.floatingShipCityDistance;
    }

    /**
     * @return The separation between each Floating Ship structure <i>(Unit: chunk)</i>
     * @author CoolCLK
     */
    public int getFloatingShipSeparation() {
        return this.floatingShipSeparation;
    }
}
