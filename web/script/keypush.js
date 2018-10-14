function KeyPush(event) {
    var pX = PositionX;
    var pY = PositionY;

    key = event.keyCode;
    if ((key == 37 || key == 65)) {//left
        PositionX -= speed;
    }
    if ((key == 38 || key == 87)) {//up
        PositionY -= speed;
    }
    if ((key == 68 || key == 39)) {//right
        PositionX += speed;
    }
    if ((key == 40 || key == 83)) {//down
        PositionY += speed;
    }

    for(var i=0; i<blocks.length; ++i) {
        var b = blocks[i];
        var rect1 = {x: PositionX, y: PositionY, width: size, height: size};
        var rect2 = {x: b.x, y: b.y, width: b.s, height: b.s};

        if (rect1.x < rect2.x + rect2.width &&
            rect1.x + rect1.width > rect2.x &&
            rect1.y < rect2.y + rect2.height &&
            rect1.height + rect1.y > rect2.y) {
            PositionX = pX;
            PositionY = pY;
            break;
        }
    }
}
