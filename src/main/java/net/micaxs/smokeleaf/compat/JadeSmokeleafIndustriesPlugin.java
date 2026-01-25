package net.micaxs.smokeleaf.compat;

import net.micaxs.smokeleaf.block.custom.BaseWeedCropBlock;
import net.micaxs.smokeleaf.block.custom.DryingRackBlock;
import net.micaxs.smokeleaf.block.custom.GrowPotBlock;
import net.micaxs.smokeleaf.block.custom.ReflectorBlock;
import net.micaxs.smokeleaf.compat.jade.DryingRackDataProvider;
import net.micaxs.smokeleaf.compat.jade.DryingRackProvider;
import net.micaxs.smokeleaf.compat.jade.GrowPotDataProvider;
import net.micaxs.smokeleaf.compat.jade.GrowPotProvider;
import net.micaxs.smokeleaf.compat.jade.ReflectorDataProvider;
import net.micaxs.smokeleaf.compat.jade.ReflectorProvider;
import net.micaxs.smokeleaf.compat.jade.WeedCropDataProvider;
import net.micaxs.smokeleaf.compat.jade.WeedCropProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeSmokeleafIndustriesPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        // Since 1.21.6, data providers must be separate classes from component providers
        registration.registerBlockDataProvider(WeedCropDataProvider.INSTANCE, BaseWeedCropBlock.class);
        registration.registerBlockDataProvider(GrowPotDataProvider.INSTANCE, GrowPotBlock.class);
        registration.registerBlockDataProvider(DryingRackDataProvider.INSTANCE, DryingRackBlock.class);
        registration.registerBlockDataProvider(ReflectorDataProvider.INSTANCE, ReflectorBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        // Client-side component providers (separate from data providers since 1.21.6)
        registration.registerBlockComponent(WeedCropProvider.INSTANCE, BaseWeedCropBlock.class);
        registration.registerBlockComponent(GrowPotProvider.INSTANCE, GrowPotBlock.class);
        registration.registerBlockComponent(DryingRackProvider.INSTANCE, DryingRackBlock.class);
        registration.registerBlockComponent(ReflectorProvider.INSTANCE, ReflectorBlock.class);
    }
}
