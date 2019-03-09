
function render(ctx, frame, dt) {
    ctx.clearRect(0, 0, width, height);
    renderMap(ctx);
    renderPlayers(ctx);
    renderPlayer(ctx, dt);
    renderBullets(ctx);
}

function renderPlayers(ctx) {
    playerUpdate();
    for(var i=0; i<players.length; ++i){
        if (players[i].id !== playerName) {
            ctx.fillStyle = COLOR.BLUE;
            ctx.fillRect(players[i].x, players[i].y, TILE, TILE);
        }
    }
}

function renderBullets(ctx) {
    $.ajax({
        url: "game/get-bullets",
        context: document.body,
        success: function(data){
            bullets = data;
        }
    });

    ctx.fillStyle = COLOR.WHITE;
    for(var i=0; i<bullets.length; ++i){
        ctx.fillRect(bullets[i].x, bullets[i].y, TILE/4, TILE/4);
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