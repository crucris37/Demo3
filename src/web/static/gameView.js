var socket = io.connect({transports: ['websocket']});
socket.on('gameState', parseGameState);

//const tileSize = 30;

var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
ctx.globalCompositeOperation = 'source-over';

function parseGameState(event) {
    console.log(event);
    var gameState = JSON.parse(event);

    ctx.beginPath();
    ctx.fillStyle = "#4fcfff";
    // change width

    // w = 1000
    // h = 800
    ctx.fillRect(5, 5, 1000, 800);







    ctx.stroke();

    for (let player of gameState['players']) {
        ctx.beginPath();
        ctx.lineWidth = "4";
        ctx.strokeStyle = player['color'];
        ctx.rect(parseFloat(player["x"]), parseFloat(player["y"]), 20, 20);
        ctx.stroke();
        for (let variable of player['body']) {
            ctx.beginPath();
            ctx.lineWidth = "4";
            ctx.strokeStyle = player['color'];
            ctx.rect(parseFloat(variable["x"]), parseFloat(variable["y"]), 20, 20);
            ctx.stroke();
        }
    }
}


var username = "";

function initializeGame(inputUsername) {
    username = inputUsername;
    socket.emit("register", username);
}
