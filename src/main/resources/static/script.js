let stompClient = null;

function connect() {
    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/public', function (message) {
            showMessage(JSON.parse(message.body).content);
        });
    });
}

function sendMessage() {
    const messageContent = document.getElementById("message").value;
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({'sender': 'user', 'content': messageContent}));
}

function showMessage(message) {
    const messageArea = document.getElementById("messageArea");
    const messageElement = document.createElement('li');
    messageElement.appendChild(document.createTextNode(message));
    messageArea.appendChild(messageElement);
}