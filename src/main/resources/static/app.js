let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log('Connected');

        // Grup mesajlarını dinle
        stompClient.subscribe('/topic/group', (message) => {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    const groupMessage = {
        sender: document.getElementById("sender").value,
        content: document.getElementById("message").value,
        groupname: document.getElementById("groupname").value,
    };
    stompClient.send('/app/chat.sendGroupMessage', {}, JSON.stringify(groupMessage));
}

function showMessage(message) {
    const messageElement = document.createElement('li');
    messageElement.innerText = `${message.sender}: ${message.content}`;
    document.getElementById('messages').appendChild(messageElement);
}

document.getElementById('connect').addEventListener('click', connect);
document.getElementById('send').addEventListener('click', sendMessage);
