const express = require('express');
const bodyParser = require('body-parser');
const sql = require('mssql');

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Azure SQL Database configuration
const config = {
  user: 'your-username',
  password: 'your-password',
  server: 'your-server-name.database.windows.net',
  database: 'your-database-name',
  options: {
    encrypt: true,
    enableArithAbort: true,
  },
};

// Test route
app.get('/', (req, res) => {
  res.send('API is working!');
});

// Example API route to fetch data from the database
app.get('/api/data', async (req, res) => {
  try {
    const pool = await sql.connect(config);
    const result = await pool.request().query('SELECT * FROM your_table_name');
    res.json(result.recordset);
  } catch (error) {
    console.error(error);
    res.status(500).send('Internal Server Error');
  }
});

app.listen(port, () => {
  console.log(`Server is running at http://localhost:${port}`);
});
