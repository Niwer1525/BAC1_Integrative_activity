package hexmo.views.effect;

import org.helmo.swing.effect.CompletableTask;

import java.util.*;

public class EffectsPlayer {
    public Deque<CompletableTask> effects = new ArrayDeque<>();

    public void push(CompletableTask effect) {
        effects.addFirst(effect);
    }

    public void update() {
        for(var it = effects.iterator(); it.hasNext(); ) {
            var current = it.next();
            if(current.isDone()) {
                it.remove();
            } else {
                current.update();
            }
        }
    }
}
