
//overrides the value assignment in the next level function because you should be able to move immediately on the first level
var controlTimer = 110;

//main game loop
function draw() {
    //for horizontal scrolling
    push();
    scale(width/800, height/500);
    if(theme === "DARK") {
        background(120);
    } else {
        background(190);
    }


    //only allows controls if a certain amount of time has passed on the timer (on a new level, etc)
    //if(controlTimer > 29) {
    //handles player input
    if(keys[UP_ARROW] || keys[87]) {
        Player.jump();
    }
    if(keys[LEFT_ARROW] || keys[65]) {
        Player.walk(-1.25);
    }
    if(keys[RIGHT_ARROW] || keys[68]) {
        Player.walk(1.25);
    }
    /*} else {
      textSize(12);
      text("Controls currently locked", 80, 15)
      controlTimer++;
    //}*/
    //updates the player's position (before drawing obviously, you don't want a 1 frame delay between input and output)
    Player.updateX();

    //checks if the update of player position caused a collision with a platform, if so, move it out
    Player.walkedInPlatform();

    //deals with the changes in Y next
    Player.updateY();


    //increases the amount of frames that the player has been off the ground
    offGround++;
    timeSinceJump++;
    //If the player is found to be touching the ground (only caused by vertical movements)
    for(i = 0; i < platforms.length; i++) {


        //handles platform collisions.
        if(platforms[i].checkCollision()) {
            while(platforms[i].checkCollision()) {
                if(Player.ySpeed > 0) {

                    //ceiling detection
                    Player.y += 0.2;
                } else {

                    //floor detection
                    offGround = 0;
                    //make this value higher for better performance but it makes things a lot less accurate should be a factor of gravity or else weird sh*t is gonna happen
                    Player.y-=0.2;
                }
            }

            //touching a ground stops all y speeds
            Player.ySpeed = 0;
        }

    }


    translate(-Player.x - Player.w/2 + 400, -Player.y - Player.h/2 + 250);

    for(i = 0; i < platforms.length; i++) {
        //draws the platform
        platforms[i].draw();
    }




    Player.draw();




    pop();

    fill(0);
    textSize(width/27);
    text("Health: " + round(Player.health), width - width/9, height/25);
    //text("FPS: " + round(frameRate()/10)*10, width - 70, 70)

    //checks if lives are all out
    if(Player.health < 0.1) {
        die();
    }
    //checks collision with portal, if there is collision, move player to the next level

}