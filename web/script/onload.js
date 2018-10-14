var PositionX = 10, PositionY = 10;
var speed = 7;
var size = 20;
var login;
var data = [];
var bX, bY, vX, vY;

var inaccuracy = 20;

window.onload = function () {
    CanvasView = document.getElementById("canvas");
    ctx = CanvasView.getContext ("2d");
    document.addEventListener("keydown",KeyPush);
    setInterval(GamePlay,50);
    bulletDataClear();
    CanvasView.onmouseup = function (e) {
        console.log(e.clientX + ";" + e.clientY);
        var toX = e.clientX;
        var toY = e.clientY;

        bX = PositionX;
        bY = PositionY;

        bSpeed = 10;
        vX = (toX - bX)/bSpeed;
        vY = (toY - bY)/bSpeed;
    };

    login = prompt("Enter you name", makeid());

};
function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 10; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

function bulletDataClear() {
    bX=0; bY=0; vX=0; vY=0;
}

function GamePlay() {

    ctx.fillStyle = "#000000";
    ctx.fillRect(0,0,CanvasView.width,CanvasView.height);

    ctx.fillStyle = "#A141FF";
    ctx.fillRect(PositionX, PositionY, size, size);


    $.ajax({
        url: "gameplay",
        type: "GET",
        cashed: false,
        data: {
            login: login,
            x: PositionX,
            y: PositionY,
            bX: bX, bY: bY,
            vX: vX, vY: vY
        },
        success: function (response) {
            data = JSON.parse(response);
        }
    });
    bulletDataClear();
    ctx.fillStyle = "#A1FFEE";
    for(var i=0; i<data.length; ++i) {
        ctx.fillRect(data[i].x, data[i].y, size, size);
    }


    ctx.fillStyle = "#FFEE88";
    for(var i=0; i<blocks.length; ++i) {
        var b = blocks[i];
        ctx.fillRect(b.x, b.y, b.s, b.s);
        // if ( Math.abs(PositionX+size-b.x) < inaccuracy &&
        //         Math.abs(PositionY-b.y) < inaccuracy)
        //     PositionX = b.x-size;
    }
}


