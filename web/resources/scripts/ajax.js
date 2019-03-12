function execute(cmd) {
    $.ajax({
        url: "game/execute",
        data: {"cmd":cmd} ,
        context: document.body,
        success: function(data){
            console.log(data);
        }
    });
}

function playerUpdate() {
    $.ajax({
        url: "game/update",
        data: {"x":player.x, "y":player.y, "id": playerName, "health": health},
        context: document.body,
        success: function(data){
            players = data;
        }
    });
}

function shoot(vX, vY) {
    $.ajax({
        url: "game/shoot",
        data: {"x":player.x, "y":player.y, "vX": vX, "vY": vY},
        context: document.body
    });
}