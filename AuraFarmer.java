package dev.aurafarmer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.item.Items;

public class AuraFarmer implements ModInitializer {
	@Override
	public void onInitialize() {
		// Golden apples already have a "consumable" component (1.6s eat time + their effects).
		// We copy it and only change the eat time to 0 seconds, so the effects are kept
		// and the apple is eaten the moment you right-click.
		DefaultItemComponentEvents.MODIFY.register(context ->
			context.modify(
				item -> item == Items.GOLDEN_APPLE || item == Items.ENCHANTED_GOLDEN_APPLE,
				(builder, item) -> {
					ConsumableComponent old = item.getComponents().get(DataComponentTypes.CONSUMABLE);
					if (old == null) return;
					builder.add(DataComponentTypes.CONSUMABLE, new ConsumableComponent(
						0.0f,
						old.useAction(),
						old.sound(),
						old.hasConsumeParticles(),
						old.onConsumeEffects()
					));
				}));
	}
}
