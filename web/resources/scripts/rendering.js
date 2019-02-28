
function render(ctx, frame, dt) {
    ctx.clearRect(0, 0, width, height);
    renderMap(ctx);
    renderPlayer(ctx, dt);
    renderPlayers(ctx, dt);
}

function renderPlayers(ctx) {
    $.ajax({
        url: "game/update",
        data: {"x":player.x, "y":player.y, "name": playerName} ,
        context: document.body,
        success: function(data){
            players = data;
        }
    });

    for(var i=0; i<players.length; ++i){
        if (players[i].name !== playerName) {
            ctx.fillStyle = COLOR.BLUE;
            ctx.fillRect(players[i].x, players[i].y, TILE, TILE);
        }
    }
}

function renderMap(ctx) {
    var x, y, cell;
    for(y = 0 ; y < MAP.th ; y++) {
        for(x = 0 ; x < MAP.tw ; x++) {
            cell = tcell(x, y);
            if (cell) {
                ctx.fillStyle = COLORS[cell - 1];
                ctx.fillRect(x * TILE, y * TILE, TILE, TILE);
            }
        }
    }
}

function renderPlayer(ctx, dt) {
    ctx.fillStyle = COLOR.YELLOW;
    ctx.fillRect(player.x + (player.dx * dt), player.y + (player.dy * dt), TILE, TILE);

    var n, max;

    ctx.fillStyle = COLOR.GOLD;
    for(n = 0, max = player.collected ; n < max ; n++)
        ctx.fillRect(t2p(2 + n), t2p(2), TILE/2, TILE/2);

    ctx.fillStyle = COLOR.SLATE;
    for(n = 0, max = player.killed ; n < max ; n++)
        ctx.fillRect(t2p(2 + n), t2p(3), TILE/2, TILE/2);
}