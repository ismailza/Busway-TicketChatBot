
document.addEventListener("DOMContentLoaded", () => {
   document.addEventListener('load', useLocation());
});

const useLocation = () => {
    // Check if Geolocation is supported
    if ("geolocation" in navigator) {
        if (confirm("Would you like to use your location?")) {
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
    }, function(error) {
        // Handle errors (user denied the request, etc)
        console.error("Error getting location: ", error);
    });
}

const getNearbyStations = (latitude, longitude) => {

}
