let sourcePosition = "";

function move(position) {
    if (sourcePosition === "") {
        sourcePosition = position;
        return;
    }

    const object = {
        "source": sourcePosition,
        "destination": position,
    }
    movePiece(object, position);
}

function movePiece(object, position) {
    $.ajax({
        url: "/move/" + document.getElementById("gameId").innerText,
        type: "PUT",
        accept: 'application/json; charset=utf-8',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(object),
        success(data) {
            let result = data;
            const team = data.team;
            document.getElementById("turn").innerText = team + " Turn";
            printGameState(result);
            changePiece(position);
        },
        error(error) {
            resetSourPosition();
            alert(error.responseText);
        }
    })
}

function changePiece(position) {
    const source = sourcePosition;
    document.getElementById(position).innerHTML = document.getElementById(source).innerHTML;
    document.getElementById(source).innerHTML = "";
    resetSourPosition();
}

function resetSourPosition() {
    sourcePosition = ""
}

function printGameState(result) {
    if (result.running === false) {
        alert(result.gameState + "가 승리했습니다.");
        document.getElementById("turn").innerText = "게임이 종료되었습니다.";
        getScore();
        return;
    }
    document.getElementById("turn").innerText = result.gameState + " Turn";
}
