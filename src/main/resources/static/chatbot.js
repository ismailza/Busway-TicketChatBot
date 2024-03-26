const getResponse = (message) => {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', `/chatbot/message`, true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.responseType = 'json';
        xhr.onload = function() {
            if (xhr.status === 200) {
                console.log("Response received: ", xhr.response);
                resolve(xhr.response);
            } else {
                console.error(`Error ${xhr.status}: ${xhr.statusText}`);
                reject(new Error(`Request failed with status ${xhr.status}`));
            }
        };
        xhr.onerror = function() {
            console.error("Request failed");
            reject(new Error("Request failed"));
        };
        xhr.send(JSON.stringify({ message }));
    });
};

const initChat = async () => {
    try {
        const botReply = await getResponse("Bonjour");

        const botMessageDiv = document.createElement('div');
        botMessageDiv.className = 'bot-message';
        botMessageDiv.innerHTML = `<p class="alert alert-primary">${botReply.reply}</p>`;
        document.querySelector('#chat-container .chat-box').appendChild(botMessageDiv);
    } catch (error) {
        console.error("Error getting bot response:", error);
    }

    const chatContainer = document.getElementById('chat-container');
    chatContainer.scrollTop = chatContainer.scrollHeight;
};

const handleRequest = async () => {
    const input = document.getElementById('chat-input');
    const message = input.value.trim();
    if (message !== '') {
        input.value = '';
        const userMessageDiv = document.createElement('div');
        userMessageDiv.className = 'user-message';
        userMessageDiv.innerHTML = `<p class="alert alert-secondary">${message}</p>`;
        document.querySelector('#chat-container .chat-box').appendChild(userMessageDiv);

        try {
            const botReply = await getResponse(message);

            const botMessageDiv = document.createElement('div');
            botMessageDiv.className = 'bot-message';
            botMessageDiv.innerHTML = `<p class="alert alert-primary">${botReply.reply}</p>`;
            document.querySelector('#chat-container .chat-box').appendChild(botMessageDiv);
            const chatContainer = document.getElementById('chat-container');
            chatContainer.scrollTop = chatContainer.scrollHeight;
            if (botReply.action === 'NEED_LOCATION') {
                useLocation();
            }
        } catch (error) {
            console.error("Error getting bot response:", error);
        }
    }
}

document.addEventListener('DOMContentLoaded', function() {
    initChat();
    document.getElementById('send-btn').addEventListener('click', handleRequest);
    document.getElementById('chat-input').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            handleRequest();
        }
    });
});

const useLocation = () => {
    // Check if Geolocation is supported
    if ("geolocation" in navigator) {
        if (confirm("Voulez-vous activer la géolocalisation pour trouver votre station la plus proche ?")) {
            getCoordinates();
        }
    } else {
        // Geolocation is not supported by this browser
        console.log("Geolocation is not supported by this browser.");
    }
}

const getCoordinates = () => {
    // Use the getCurrentPosition method to get the current position
    navigator.geolocation.getCurrentPosition(function(position) {
        // Access the position's latitude and longitude
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;

        // Log the latitude and longitude to the console
        console.log("Latitude: " + latitude + ", Longitude: " + longitude);

        const coordonate = "Votre coordonnée est : " + latitude + ", " + longitude;
        const input = document.getElementById('chat-input');
        input.value = coordonate;
        document.getElementById('send-btn').click();
    }, function(error) {
        // Handle errors (user denied the request, etc)
        console.error("Error getting location: ", error);
    });
}
