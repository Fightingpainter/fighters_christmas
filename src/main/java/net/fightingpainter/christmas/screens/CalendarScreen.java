package net.fightingpainter.christmas.screens;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.ItemComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.component.TextureComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.core.Component;
import io.wispforest.owo.ui.core.HorizontalAlignment;
import io.wispforest.owo.ui.core.Insets;
import io.wispforest.owo.ui.core.OwoUIAdapter;
import io.wispforest.owo.ui.core.ParentComponent;
import io.wispforest.owo.ui.core.Sizing;
import io.wispforest.owo.ui.core.Surface;
import io.wispforest.owo.ui.core.VerticalAlignment;

import net.fightingpainter.christmas.Main;
import net.fightingpainter.christmas.packets.client.ClientPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class CalendarScreen extends BaseOwoScreen<FlowLayout> {
    
    //vars
    private int day;
    private boolean collected;
    private List<ItemStack> items;
    
    //parts
    ButtonComponent closeButton = (ButtonComponent) Components.button(Text.literal("X"), close_button -> close()).sizing(Sizing.fixed(20), Sizing.fixed(20));
    TextureComponent unopenedBackground = Components.texture(new Identifier(Main.MOD_ID, "textures/gui/calendar.png"), 0, 0, 100, 100, 100 , 100);
    TextureComponent openedBackground = Components.texture(new Identifier(Main.MOD_ID, "textures/gui/calendar_open.png"), 0, 0, 100, 100, 100 , 100);
    LabelComponent collectLabel = Components.label(Text.of("already collected"));
    
    public CalendarScreen(int day, boolean collected, List<ItemStack> items) {
        
        this.day = day;
        this.collected = collected;
        this.items = items;
        
    }
    
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    
    @Override
    public boolean shouldPause() {
        return false; // don't pause the game
    }
    
    @Override
    protected void build(FlowLayout rootComponent) {
        
        LabelComponent title = Components.label(Text.of("Day "+day));
        
        ButtonComponent collectButton = Components.button(Text.literal("Collect"), collect_button -> { ClientPacket.send("collect", day); close();});
    
        ParentComponent top_title = Containers.horizontalFlow(Sizing.fixed(100), Sizing.content())
        .child(title)
        .verticalAlignment(VerticalAlignment.CENTER)
        .horizontalAlignment(HorizontalAlignment.CENTER);
        
        ParentComponent top_close = Containers.horizontalFlow(Sizing.fixed(100), Sizing.content())
        .child(closeButton)
            .verticalAlignment(VerticalAlignment.CENTER)
            .horizontalAlignment(HorizontalAlignment.RIGHT);

        ParentComponent top = Containers.stack(Sizing.fixed(100), Sizing.content())
            .child(top_title)
            .child(top_close)
            .verticalAlignment(VerticalAlignment.CENTER);

        GridLayout itemBox = Containers.grid(Sizing.content(), Sizing.content(), 3, 3);

        for (int i = 0; i < items.size(); i++) {

            int x;
            int y;
            
            switch (i) {
                case 0:
                    x = 0;// XOO
                    y = 0;// OOO
                    break;// OOO
            
                case 1:
                    x = 1;// OXO
                    y = 0;// OOO
                    break;// OOO
                
                case 2:
                    x = 0;// OOO
                    y = 1;// XOO
                    break;// OOO

                case 3:
                    x = 1;// OOO
                    y = 1;// OXO
                    break;// OOO
                
                case 4:
                    x = 2;// OOX
                    y = 0;// OOO
                    break;// OOO
                
                case 5:
                    x = 2;// OOO
                    y = 1;// OOX
                    break;// OOO
                
                case 6:
                    x = 0;// OOO
                    y = 2;// OOO
                    break;// XOO
                
                case 7:
                    x = 1;// OOO
                    y = 2;// OOO
                    break;// OXO
                
                case 8:
                    x = 2;// OOO
                    y = 2;// OOO
                    break;// OOX

                default:
                    x = 0;
                    y = 0;
                    break;
            }

            ItemComponent item = Components.item(items.get(i)).setTooltipFromStack(true).showOverlay(true);
            itemBox.child(item, x, y);

        }


        TextureComponent backgroundTexture;
        Component collect;
        Component rewardBox;

        if (collected) {
            backgroundTexture = openedBackground;
            collect = collectLabel;

        } else {
            backgroundTexture = unopenedBackground;
            collect = collectButton;
        }

        if ((day > 0 && day <= 24) && items.size() > 0) {
            rewardBox = itemBox;
        } else if (day == 25) {

            MutableText message = Text.literal("No More Rewards\nyou can still collect the rewards you missed by using the ").append(
                Text.literal("\"/calendar list missed\"").formatted(Formatting.BLUE)
                .styled(style -> style
                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/calendar list missed"))
                )

            ).append(Text.literal(" command"));

            rewardBox = Components.label(message);
            collect = Components.label(Text.of("Nothing to collect"));
        }
        else {
            rewardBox = Components.label(Text.of("There has either been an error or the reward has not been set correctly or at all.\nEither way, please report this to me since this is not supposed to happen."));
            collect = Components.label(Text.of("Nothing to collect"));
        }

        ParentComponent box = Containers.stack(Sizing.content(), Sizing.content())
            .child(backgroundTexture)
            .child(rewardBox)
            .padding(Insets.of(10))
            .verticalAlignment(VerticalAlignment.CENTER)
            .horizontalAlignment(HorizontalAlignment.CENTER);



        ParentComponent bottom = Containers.horizontalFlow(Sizing.content(), Sizing.content())
            .child(collect)
            .verticalAlignment(VerticalAlignment.CENTER)
            .horizontalAlignment(HorizontalAlignment.CENTER);

        rootComponent
            .surface(Surface.VANILLA_TRANSLUCENT)
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .verticalAlignment(VerticalAlignment.CENTER);

        rootComponent.child(
                Containers.verticalFlow(Sizing.content(), Sizing.content())
                    .child(top)
                    .child(box)
                    .child(bottom)
                    .verticalAlignment(VerticalAlignment.CENTER)
                    .horizontalAlignment(HorizontalAlignment.CENTER)
        );
    }

}