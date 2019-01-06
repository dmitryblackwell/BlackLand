var levels = [
    [
        "  Z           GG",
        "GGGG",
        30
    ]
];

function nextLevel() {
    if(level > levels.length) {
        win();
    }
    backupMonsters = [];
    //resets the player's health
    Player.health = 100;
    reset();
    //last item in the level data is the size of each square, the grid size.
    var gridSize = levels[level - 1][levels[level - 1].length - 1];
    for(var i = 0; i < levels[level - 1].length - 1; i++) {
        for(var j = 0; j < levels[level - 1][i].length; j++) {
            switch(levels[level - 1][i].charAt(j)) {
                case "G":
                    platforms.push(new Platform(j*gridSize, i*gridSize, gridSize, gridSize));
                    break;
                case "Z":
                    Player.x = j * gridSize + 0.5;
                    Player.y = i * gridSize + 0.5;

                    //-1 is for some subtle collision changes that are for the better
                    Player.w = gridSize*0.9;
                    Player.h = gridSize*0.9;

                    //the original position of the player in a level, used to reset the player to that position when you die
                    originalCoords = [Player.x, Player.y];
                    break;
            }
        }
    }
    controlTimer = 0;
}

function win() {
    background(245);
    textSize(width / 16)
    text("You beat all levels.", width / 2, height / 2);
    noLoop();
    return;
}
function die() {
    bullets = [];
    monsters = [];
    for(var i = 0; i < backupMonsters.length; i++) {
        monsters.push(Object.assign({}, backupMonsters[i]));
    }
    Player.health = 100;
    Player.x = originalCoords[0];
    Player.y = originalCoords[1];
}
function reset() {
    //makes sure any movements don't carry from the previous level
    Player.xSpeed = 0;
    Player.ySpeed = 0;
    //resets all arrays for usage of next level
    platforms = [];
}
