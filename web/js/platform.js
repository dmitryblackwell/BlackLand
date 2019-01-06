//handles rect and rect collisions
function rectrect(x1, y1, w1, h1, x2, y2, w2, h2) {

    //Uhh for some reason I can't quite figure out how to do a rectrect collision function with rectmode corner, so I just converted the parameters as if it were rectmode center.
    x1 += w1/2;
    y1 += h1/2;
    x2 += w2/2;
    y2 += h2/2;

    return Math.abs(x1 - x2) <= w1/2 + w2/2 && Math.abs(y1 - y2) <= h1/2 + h2/2;
}
//the more complicated collision detection between a circle and rect.
function circlerect(circ, rect) {
    var distX = Math.abs(circ.x - rect.x - rect.w/2);
    var distY = Math.abs(circ.y - rect.y - rect.h/2);

    if (distX > (rect.w/2 + circ.r)) { return false; }
    if (distY > (rect.h/2 + circ.r)) { return false; }

    if (distX <= (rect.w/2)) { return true; }
    if (distY <= (rect.h/2)) { return true; }

    var dx=distX-rect.w/2;
    var dy=distY-rect.h/2;
    return (dx*dx+dy*dy<=(circ.r*circ.r));
}
//platform prototype
function Platform(x, y, w, h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.draw = function() {
        noStroke();
        if(theme === "DARK") {
            //dark dark brown
            fill(15, 10, 0);
        } else {
            //light light yellow
            fill(242);
        }
        rect(this.x, this.y, this.w, this.h);

    }
    this.checkCollision = function() {
        return rectrect(this.x, this.y, this.w, this.h, Player.x, Player.y, Player.w, Player.h);
    }
}