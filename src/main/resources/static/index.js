
document.addEventListener("DOMContentLoaded", () => {
   document.addEventListener('load', useLocation());
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
        getNearestStations(latitude, longitude);
    }, function(error) {
        // Handle errors (user denied the request, etc)
        console.error("Error getting location: ", error);
    });
}

const getNearestStations = (latitude, longitude) => {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/api/nearestStation?latitude=${latitude}&longitude=${longitude}`, true);
    xhr.send();

    xhr.onload = function() {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.response);
            const nearestStationId = response.id;
            const nearestStationName = response.name;
            // Update the dropdown to select the nearest station
            const select = document.getElementById('stationDepart');
            const option = document.createElement('option');
            option.value = nearestStationId;
            option.text = nearestStationName;
            option.selected = true;
            option.classList.add('text-dark', 'font-weight-bold', 'bg-info');
            select.insertBefore(option, select.firstChild);

            const displayDiv = document.getElementById('nearestStationDisplay');
            displayDiv.style.display = 'block';
            displayDiv.innerHTML = `Votre station la plus proche est : ${nearestStationName}`;
        } else {
            console.error(`Error ${xhr.status}: ${xhr.statusText}`);
        }
    };

    xhr.onerror = function() {
        console.error("Request failed");
    };
};

