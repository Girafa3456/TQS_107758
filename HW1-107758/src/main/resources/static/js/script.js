async function submitBooking(event, mealId) {
    event.preventDefault();
    console.log("Form submission intercepted"); 
    
    const form = event.target;
    const studentName = form.studentName.value;
    
    try {
        console.log("Attempting to book meal", mealId, studentName); 
        
        const response = await fetch('/api/bookings/book', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                mealId: mealId, 
                studentName: studentName 
            })
        });

        console.log("Response status:", response.status); 
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const reservation = await response.json();
        console.log("Reservation created:", reservation); 
        
        // Redirect to confirmation page
        window.location.href = `/confirm/${reservation.token}`;
    } catch (error) {
        console.error("Booking failed:", error);
        alert('Failed to book meal. Please try again.');
    }
}