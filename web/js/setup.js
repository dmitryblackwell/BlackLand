//Player object for current level is
function setup() {
    //16:10 = MASTERRACE
    //if the user's screen has more width for 16:10 than height, put bars on the side, otherwise put it at the top
    if(windowWidth*5/8 > windowHeight) {
        createCanvas(windowHeight*8/5, windowHeight);
    } else{
        createCanvas(windowWidth, windowWidth*5/8);
    }
    textAlign(CENTER, CENTER);
    background(250);
    //don't change, it does nothing. Couldn't get this to work so I quit;
    var playerColor = color(255, 0, 0);


    frameRate(60);
}

function windowResized() {
    //if the user's screen has more width for 16:10 than height, put bars on the side, otherwise put it at the top
    if(windowWidth*5/8 > windowHeight) {
        resizeCanvas(windowHeight*8/5, windowHeight);
    } else{
        resizeCanvas(windowWidth, windowWidth*5/8);
    }
}


var platforms = [];

//timer for using controls. for example. when you go to a new level, the control timer will be set to 0 and not allow controls until it hits 30
var controlTimer = 110;

//assume you are off the ground at the start to prevent jumping before you touch a ground
var offGround = 10;

//prevents jumping just a few frames after the inital jump
var timeSinceJump = 1111;

var level = 1;
//self-explanatory
var gravity = 0.8;

//the state of antilag mode
var antilag = false;

//Light or dark theme
var theme = "DARK";
nextLevel();