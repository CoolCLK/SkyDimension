package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.world.gen.structure.MapGenFloatingShip;
import net.minecraft.command.CommandLocate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = CommandLocate.class, priority = 1001)
public class MixinCommandLocate {
    @Redirect(method = "getTabCompletions", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/CommandLocate;getListOfStringsMatchingLastWord([Ljava/lang/String;[Ljava/lang/String;)Ljava/util/List;"))
    private List<String> injectTabCompletions(String[] args, String... possibilities) {
        List<String> possibilitieList = new ArrayList<>(Arrays.asList(possibilities));
        possibilitieList.add(MapGenFloatingShip.getName());
        return CommandLocate.getListOfStringsMatchingLastWord(args, possibilitieList);
    }
}
