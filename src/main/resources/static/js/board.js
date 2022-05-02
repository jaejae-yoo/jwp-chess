function startGame() {
    const url = window.location.href.split('/');
    $.ajax({
        url: "/games/" + url[url.length - 1],
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        type: "get",
        success: function (data) {
            const url = window.location.href.split('/');
            document.getElementById("gameId").innerText = url[url.length - 1]
            if (data.pieces.length === 0) {
                location.reload();
            }
            const pieces = data.pieces;
            const team = data.team;
            document.getElementById("turn").innerText = team + " Turn";
            $.each(pieces, function(index, piece) {
                findById(piece.position, piece.symbol);
            })

        },
        error: function (data){
            alert(JSON.stringify(data))
            alert(data)
        }
    })
}

function findById(position, symbol) {
    if (piece[symbol] !== undefined) {
        document.getElementById(position).innerHTML = piece[symbol];
    }
}

let piece = {
    "P": '<img src="../img/piece/pawn_black.png" width="80px" height="80px"/>',
    "p": '<img src="../img/piece/pawn_white.png"  width="80px" height="80px"/>',
    "R": '<img src="../img/piece/rook_black.png" width="80px" height="80px"/>',
    "r": '<img src="../img/piece/rook_white.png" width="80px" height="80px"/>',
    "B": '<img src="../img/piece/bishop_black.png" width="80px" height="80px"/>',
    "b": '<img src="../img/piece/bishop_white.png" width="80px" height="80px"/>',
    "N": '<img src="../img/piece/knight_black.png" width="80px" height="80px"/>',
    "n": '<img src="../img/piece/knight_white.png" width="80px" height="80px"/>',
    "Q": '<img src="../img/piece/queen_black.png" width="80px" height="80px"/>',
    "q": '<img src="../img/piece/queen_white.png" width="80px" height="80px"/>',
    "K": '<img src="../img/piece/king_black.png" width="80px" height="80px"/>',
    "k": '<img src="../img/piece/king_white.png" width="80px" height="80px"/>',
}

function reset() {
    $.ajax({
        url: "/reset/" + document.getElementById("gameId").innerText,
        type: "POST",
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            const team = data.team;
            const pieces = data.pieces;

            document.getElementById("gameId").innerText = data.id;
            document.getElementById("turn").innerText = team + " Turn";
            location.reload()
            $.each(pieces, function(index, piece) {
                findById(piece.position, piece.symbol);
            })
            
        },
        error: function (data){
            alert(data);
        }
    })
}

function end() {
    $.ajax({
        url: "/end/" + document.getElementById("gameId").innerText,
        type: "PUT",
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            if (data.running === true) {
                const winningTeam = data.gameState;
                alert("게임이 종료되었습니다." + "우승자는 "+ winningTeam);
            }
        },
        error: function (data){
            alert(data);
        }
    })
}
