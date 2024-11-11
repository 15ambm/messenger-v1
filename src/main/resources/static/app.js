let stompClient = null;

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        // Subscribe to the public topic for real-time messages
        stompClient.subscribe('/topic/public', (message) => {
            showMessage(JSON.parse(message.body));
        });

        // Fetch message history when connected
        fetch('/chat/history')
            .then(response => response.json())
            .then(messages => {
                messages.forEach(showMessage);  // Display each message from history
            });
    });
}

function showMessage(message) {
    const chat = document.getElementById('chat');
    const messageElement = document.createElement('p');
    messageElement.textContent = `${message.sender}: ${message.content}`;
    chat.appendChild(messageElement);

    // Auto-scroll to the bottom
    chat.scrollTop = chat.scrollHeight;
}

document.getElementById('messageForm').addEventListener('submit', (e) => {
    e.preventDefault();

    const sender = document.getElementById('sender').value;
    const message = document.getElementById('message').value;

    if (stompClient && stompClient.connected) {
        stompClient.send("/app/chat/message", {}, JSON.stringify({ sender, content: message }));
        document.getElementById('message').value = '';
    }
});

connect();
