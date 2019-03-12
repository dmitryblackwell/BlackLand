if (!window.requestAnimationFrame) { // http://paulirish.com/2011/requestanimationframe-for-smart-animating/
    window.requestAnimationFrame = window.webkitRequestAnimationFrame ||
        window.mozRequestAnimationFrame    ||
        window.oRequestAnimationFrame      ||
        window.msRequestAnimationFrame     ||
        function(callback, element) {
            window.setTimeout(callback, 1000 / 60);
        }
}



var MAP      = { tw: 64, th: 48 },
    TILE     = 32,
    METER    = TILE,
    GRAVITY  = 9.8 * 4 // default (exagerated) gravity
MAXDX    = 25,      // default max horizontal speed (15 tiles per second)
    MAXDY    = 100,      // default max vertical speed   (60 tiles per second)
    ACCEL    = 1/2,     // default take 1/2 second to reach maxdx (horizontal acceleration)
    FRICTION = 1/6,     // default take 1/6 second to stop from maxdx (horizontal friction)
    IMPULSE  = 1500,    // default player jump impulse
    COLOR    = { BLACK: '#000000', YELLOW: '#ECD078', BRICK: '#D95B43', PINK: '#C02942', PURPLE: '#542437', GREY: '#333', SLATE: '#53777A', GOLD: 'gold', WHITE: "white", BLUE: "blue" },
    COLORS   = [ COLOR.YELLOW, COLOR.BRICK, COLOR.PINK, COLOR.PURPLE, COLOR.GREY ],
    KEY      = { SPACE: 32, LEFT: 37, UP: 38, RIGHT: 39, DOWN: 40, A: 65, D: 68, W: 87 };

var fps      = 60,
    step     = 1/fps,
    canvas   = document.getElementById('canvas'),
    ctx      = canvas.getContext('2d'),
    bb       = canvas.getBoundingClientRect(),
    offsetX  = bb.left,
    offsetY  = bb.top,
    width    = MAP.tw * TILE,
    height   = MAP.th * TILE,
    player   = {},
    health = 100;
    playerName= generateName(),
    players  = [],
    monsters = [],
    treasure = [],
    cells    = [];

var t2p      = function(t)     { return t*TILE;                  },
    p2t      = function(p)     { return Math.floor(p/TILE);      },
    cell     = function(x,y)   { return tcell(p2t(x),p2t(y));    },
    tcell    = function(tx,ty) { return cells[tx + (ty*MAP.tw)]; };


function setup(map) {
    var data    = map.layers[0].data,
        objects = map.layers[1].objects,
        n, obj, entity;

    for(n = 0 ; n < objects.length ; n++) {
        obj = objects[n];
        entity = setupEntity(obj);
        switch(obj.type) {
            case "player"   : player = entity; break;
            case "monster"  : monsters.push(entity); break;
            case "treasure" : treasure.push(entity); break;
        }
    }

    cells = data;
}

function setupEntity(obj) {
    var entity = {};
    entity.x        = obj.x;
    entity.y        = obj.y;
    entity.dx       = 0;
    entity.dy       = 0;
    entity.gravity  = METER * (obj.properties.gravity || GRAVITY);
    entity.maxdx    = METER * (obj.properties.maxdx   || MAXDX);
    entity.maxdy    = METER * (obj.properties.maxdy   || MAXDY);
    entity.impulse  = METER * (obj.properties.impulse || IMPULSE);
    entity.accel    = entity.maxdx / (obj.properties.accel    || ACCEL);
    entity.friction = entity.maxdx / (obj.properties.friction || FRICTION);
    entity.monster  = obj.type == "monster";
    entity.player   = obj.type == "player";
    entity.treasure = obj.type == "treasure";
    entity.player.health = obj.properties.health;
    entity.left     = obj.properties.left;
    entity.right    = obj.properties.right;
    entity.start    = { x: obj.x, y: obj.y }
    entity.killed = entity.collected = 0;
    return entity;
}