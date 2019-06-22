package com.blackwell;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@EqualsAndHashCode(exclude = {"x", "y"})
public class Player {
    String login;
    int x, y;

    void update(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
