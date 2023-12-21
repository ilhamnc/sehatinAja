const express = require('express');
const bodyParser = require('body-parser');
const { PythonShell } = require('python-shell');

const app = express();
const port = 5000;

app.use(bodyParser.json());

app.post('/predict', (req, res) => {
  const { umur, berat_badan, tinggi_badan } = req.body;

  let options = {
    mode: 'text',
    pythonOptions: ['-u'],
    scriptPath: '../../../backend-ml', // Ganti dengan path ke skrip Python Anda
    args: [umur, berat_badan, tinggi_badan]
  };

  PythonShell.run('model.py', options, (err, result) => {
    if (err) throw err;
    console.log('result: ', result);
    res.json({ prediction: result });
  });
});

app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});