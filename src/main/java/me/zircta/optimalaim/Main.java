package me.zircta.optimalaim;

import com.gitlab.candicey.zenithloader.ZenithLoader;
import com.gitlab.candicey.zenithloader.dependency.Dependencies;
import me.zircta.optimalaim.config.OptimalAimConfig;
import me.zircta.optimalaim.listeners.RenderLivingListener;
import net.weavemc.loader.api.ModInitializer;
import net.weavemc.loader.api.event.EventBus;
import net.weavemc.loader.api.event.StartGameEvent;

public class Main implements ModInitializer {
    public static OptimalAimConfig config;

    @Override
    public void preInit() {
        ZenithLoader.INSTANCE.loadDependencies(
                Dependencies.INSTANCE.getConcentra().invoke(
                        "optimalaim"
                )
        );

        config = new OptimalAimConfig();

        EventBus.subscribe(StartGameEvent.Pre.class, (event) -> EventBus.subscribe(new RenderLivingListener()));
    }
}
