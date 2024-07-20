package com.kingmang.lazurite.libraries.lzrx.awt.lgl.event;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import lombok.Getter;

@Getter
public enum Events {
        DRAG_DETECTED(MouseEvent.DRAG_DETECTED),
        MOUSE_CLICKED(MouseEvent.MOUSE_CLICKED),
        MOUSE_DRAGGED(MouseEvent.MOUSE_DRAGGED),
        MOUSE_ENTERED(MouseEvent.MOUSE_ENTERED),
        MOUSE_ENTERED_TARGET(MouseEvent.MOUSE_ENTERED_TARGET),
        MOUSE_EXITED(MouseEvent.MOUSE_EXITED),
        MOUSE_EXITED_TARGET(MouseEvent.MOUSE_EXITED_TARGET),
        MOUSE_MOVED(MouseEvent.MOUSE_MOVED),
        MOUSE_PRESSED(MouseEvent.MOUSE_PRESSED),
        MOUSE_RELEASED(MouseEvent.MOUSE_RELEASED),
        
        KEY_PRESSED(KeyEvent.KEY_PRESSED),
        KEY_RELEASED(KeyEvent.KEY_RELEASED),
        KEY_TYPED(KeyEvent.KEY_TYPED),
        
        SWIPE_DOWN(SwipeEvent.SWIPE_DOWN),
        SWIPE_LEFT(SwipeEvent.SWIPE_LEFT),
        SWIPE_RIGHT(SwipeEvent.SWIPE_RIGHT),
        SWIPE_UP(SwipeEvent.SWIPE_UP);

        private final EventType<? extends Event> handler;

        Events(EventType<? extends Event> handler) {
            this.handler = handler;
        }

}