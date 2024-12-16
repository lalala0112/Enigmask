'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');
const registerForm = document.querySelector('#registerForm');
const registerLink = document.querySelector('#register-link');
const registerPage = document.querySelector('#register-page');

let stompClient = null;
let username = null;
let password = null;
let selectedUserId = null;
let confirmPassword = null;

function connect(event) {
    username = document.querySelector('#username').value.trim();
    password = document.querySelector('#password').value.trim();


    if (username && password) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/topic/public`, onMessageReceived);

    // register the connected user
    stompClient.send("/app/user.addUser",
        {},
        JSON.stringify({username: username, password: password, status: 'ONLINE'})
    );

    const connectedUserElement = document.querySelector('#connected-user-username');

    if (connectedUserElement) {
        connectedUserElement.textContent = username;
    } else {
        console.error("Error: The element '#connected-user-username' does not exist in the DOM.");
    }

    findAndDisplayConnectedUsers().then();
}

async function findAndDisplayConnectedUsers() {
    const connectedUsersResponse = await fetch('/users');

    let connectedUsers;

    try {
        const responseData = await connectedUsersResponse.json();
        console.log("Connected users response:", responseData);

        // Handle the structure of the response
        if (Array.isArray(responseData)) {
            connectedUsers = responseData;
        } else if (responseData.users && Array.isArray(responseData.users)) {
            connectedUsers = responseData.users;
        } else if (responseData.username) {
            connectedUsers = [responseData];
        } else {
            connectedUsers = [];
        }

    } catch (error) {
        console.error("Error parsing /users response:", error);
        return;
    }

    // Display the connected users
    const filteredUsers = connectedUsers.filter(user => user.username !== username);
    const connectedUsersList = document.getElementById('connectedUsers');
    connectedUsersList.innerHTML = '';

    filteredUsers.forEach(user => {
        appendUserElement(user, connectedUsersList);
        if (filteredUsers.indexOf(user) < filteredUsers.length - 1) {
            const separator = document.createElement('li');
            separator.classList.add('separator');
            connectedUsersList.appendChild(separator);
        }
    });
}

function appendUserElement(user, connectedUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('user-item');
    listItem.id = user.username;

    const userImage = document.createElement('img');
    userImage.src = '../image/user_icon.png';
    userImage.alt = user.username;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.username;

    const receivedMsgs = document.createElement('span');
    receivedMsgs.textContent = '0';
    receivedMsgs.classList.add('nbr-msg', 'hidden');

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);
    listItem.appendChild(receivedMsgs);

    listItem.addEventListener('click', userItemClick);

    connectedUsersList.appendChild(listItem);
}

function userItemClick(event) {
    document.querySelectorAll('.user-item').forEach(item => {
        item.classList.remove('active');
    });
    messageForm.classList.remove('hidden');

    const clickedUser = event.currentTarget;
    clickedUser.classList.add('active');

    selectedUserId = clickedUser.getAttribute('id');
    fetchAndDisplayUserChat().then();

    const nbrMsg = clickedUser.querySelector('.nbr-msg');
    nbrMsg.classList.add('hidden');
    nbrMsg.textContent = '0';

}

function displayMessage(senderId, content) {
    const messageContainer = document.createElement('div');
    messageContainer.classList.add('message');
    if (senderId === username) {
        messageContainer.classList.add('sender');
    } else {
        messageContainer.classList.add('receiver');
    }
    const message = document.createElement('p');
    message.textContent = content;
    messageContainer.appendChild(message);
    chatArea.appendChild(messageContainer);
}

async function fetchAndDisplayUserChat() {
    const userChatResponse = await fetch(`/queue/messages/${username}/${selectedUserId}`);
    const userChat = await userChatResponse.json();
    chatArea.innerHTML = '';

    userChat.forEach(chat => {
        displayMessage(chat.senderId, chat.content);
    });

    chatArea.scrollTop = chatArea.scrollHeight;
}


function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            senderId: username,
            recipientId: selectedUserId,
            content: messageInput.value.trim(),
            timestamp: new Date()
        };
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
        displayMessage(username, messageInput.value.trim());
        messageInput.value = '';
    }
    chatArea.scrollTop = chatArea.scrollHeight;
    event.preventDefault();
}

async function onMessageReceived(payload) {
    await findAndDisplayConnectedUsers();
    console.log('Message received', payload);
    const message = JSON.parse(payload.body);
    if (selectedUserId && selectedUserId === message.senderId) {
        displayMessage(message.senderId, message.content);
        chatArea.scrollTop = chatArea.scrollHeight;
    }

    if (selectedUserId) {
        document.querySelector(`#${selectedUserId}`).classList.add('active');
    } else {
        messageForm.classList.add('hidden');
    }

    const notifiedUser = document.querySelector(`#${message.senderId}`);
    if (notifiedUser && !notifiedUser.classList.contains('active')) {
        const nbrMsg = notifiedUser.querySelector('.nbr-msg');
        nbrMsg.classList.remove('hidden');
        nbrMsg.textContent = '';
    }
}

function onLogout() {
    stompClient.send("/app/user.disconnectUser",
        {},
        JSON.stringify({username: username, password: password, status: 'OFFLINE'})
    );
    window.location.reload();
}

function jump_reg() {
    usernamePage.classList.add('hidden');
    registerPage.classList.remove('hidden');
}

function register(event) {
    username = document.querySelector('#r_nickname').value.trim();
    password = document.querySelector('#r_fullname').value.trim();
    confirmPassword = document.querySelector('#r2_fullname').value.trim();

    if (password !== confirmPassword) {
        alert('密碼不一致，請重新輸入。');
        return;
    }

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    // 寫入資料庫
    stompClient.connect({}, function() {
        console.log('STOMP 連接成功，發送用戶註冊請求...');

        stompClient.send("/app/user.addUser", {},
            JSON.stringify({username: username, password: password}));

        alert('註冊成功，請登入。');
        registerPage.classList.add('hidden');
        usernamePage.classList.remove('hidden');
        }, function(error) {
            console.error('STOMP 連接失敗: ', error);
            alert('無法連接到伺服器，請稍後再試');
        });
    event.preventDefault();
}

usernameForm.addEventListener('submit', connect, true); // step 1
registerLink.addEventListener('click', jump_reg, true);
registerForm.addEventListener('submit', register, true);
messageForm.addEventListener('submit', sendMessage, true);
logout.addEventListener('click', onLogout, true);
window.onbeforeunload = () => onLogout();
