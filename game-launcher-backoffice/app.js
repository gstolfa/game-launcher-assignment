// app.js
const express = require('express');
const path = require('path');
const pool = require('./src/config/db');
require('dotenv').config();

const app = express();

// Set the EJS view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'src/views'));

// Set the port
const PORT = process.env.PORT || 3000;

// Helper function to format today's date in the "31 October 2024" format
const getFormattedDate = () => {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const monthNames = ["January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"];
    const month = monthNames[today.getMonth()];
    const year = today.getFullYear();
    return `${day} ${month} ${year}`;
};

// Main route to display game sessions
app.get('/', async (req, res) => {
    try {
        const [rows] = await pool.query(`
            SELECT game_code, session_id, created_at 
            FROM game_session 
            WHERE DATE(created_at) = CURDATE()
        `);
        res.render('index', { sessions: rows, currentDate: getFormattedDate() });
    } catch (error) {
        console.error('Error retrieving game sessions:', error);
        res.status(500).send('Internal server error');
    }
});

// Error handling for routes not found
app.use((req, res) => {
    res.status(404).send('Page not found');
});

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
