//current keys that are being held down
var keys = [];
function keyPressed() {
    //again, like I said in my previous P5 program, I'm not sure how this assigning values to nonexistent items in a list works. Enlighten me if you're reading this and know how
    keys[keyCode] = true;
    //R = restart
    if(keyCode === 82) {
        die();
    }
    //l = toggle lag prevention
    if(keyCode === 76) {
        if(antilag) {
            antilag = false;
        } else {
            antilag = true;
        }
    }
    if(keyCode === 84) {
        if(theme === "DARK") {
            theme = "LIGHT";
        } else {
            theme = "DARK";
        }
    }
}
function keyReleased() {
    keys[keyCode] = false;
}