var Player = {
    x: 385,
    y: 400,
    w: 30,
    h: 30,
    ySpeed: 0,
    xSpeed: 0,
    health: 100
}

//the jump
Player.jump = function() {
    //only execute if the player has been on the ground VERY recently
    if(offGround < 3 && timeSinceJump > 2) {
        //fiddle with this to change the jump height
        this.ySpeed = 16;
        timeSinceJump = 0;
    }
}
Player.walk = function(dir) {
    this.xSpeed += dir;
}

//checks platform collision in the x-direction
Player.walkedInPlatform = function() {

    for(var i = 0; i < platforms.length; i++) {
        //check if they are in a wall, if so, move the f**k out!
        slope = 0;
        while(slope < 20 && platforms[i].checkCollision()) {
            this.y-=0.2;
            slope++;
        }
        if(slope === 20) {

            this.x -= this.xSpeed;
            this.xSpeed = 0;
            this.y += slope*0.2;
        }
    }

}

//updates the position of the player based on current speeds
Player.updateX = function() {
    this.xSpeed *= 0.8;
    this.x += this.xSpeed;
}
Player.updateY = function() {
    //it's weird cuz positive y is downwards...
    if(this.ySpeed < 4 || keys[UP_ARROW] || keys[87]) {//to make higher jumps the longer the up arrow is pressed
        this.ySpeed -= gravity;
    } else {
        this.ySpeed -= gravity * 2;
    }
    //so I just flip the y! Smart right?
    this.y -= this.ySpeed;


    if(this.y > levels[level - 1].length * levels[level - 1][levels[level - 1].length - 1]) {
        die();
    }
}
Player.draw = function() {
    noStroke();
    fill(255, 0, 0);
    rect(this.x, this.y, this.w, this.h, this.w / 10);
    fill(0);

    //jump costumes
    if(this.ySpeed > 0.5) {
        //jump looking right
        if(this.xSpeed > 0.8) {

            //eyes
            rect(this.x + this.w * 0.3, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.75, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.40, this.y + this.h * 0.54, this.w * 0.4, this.h * 0.25);
        } else if(this.xSpeed < -0.8) {//jump looking left

            //eyes
            rect(this.x + this.w * 0.1, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.55, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.20, this.y + this.h * 0.54, this.w * 0.4, this.h * 0.25);
        } else {//jump regular

            //eyes
            rect(this.x + this.w * 0.2, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.65, this.y + this.h * 0.22, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.30, this.y + this.h * 0.54, this.w * 0.4, this.h * 0.25);
        }
    } else if(this.ySpeed < -3.3) {//falling
        //falling looking right
        if(this.xSpeed > 0.8) {

            //eyes
            rect(this.x + this.w * 0.3, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.75, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.40, this.y + this.h * 0.73, this.w * 0.4, this.h * 0.25);
        } else if(this.xSpeed < -0.8) {//falling looking left

            //eyes
            rect(this.x + this.w * 0.1, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.55, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.20, this.y + this.h * 0.73, this.w * 0.4, this.h * 0.25);
        } else {//falling regular

            //eyes
            rect(this.x + this.w * 0.2, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.65, this.y + this.h * 0.43, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.30, this.y + this.h * 0.73, this.w * 0.4, this.h * 0.25);
        }
    } else {
        //looking right
        if(this.xSpeed > 0.8) {

            //eyes
            rect(this.x + this.w * 0.3, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.75, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.40, this.y + this.h * 2 / 3, this.w * 0.4, this.h * 0.25);
        } else if(this.xSpeed < -0.8) {//looking left
            //eyes
            rect(this.x + this.w * 0.1, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.55, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.20, this.y + this.h * 2 / 3, this.w * 0.4, this.h * 0.25);
        } else {//regular
            //eyes
            rect(this.x + this.w * 0.2, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);
            rect(this.x + this.w * 0.65, this.y + this.h / 3, this.w * 0.15, this.h * 0.15);

            //mouth
            rect(this.x + this.w * 0.30, this.y + this.h * 2 / 3, this.w * 0.4, this.h * 0.25);
        }
    }
}