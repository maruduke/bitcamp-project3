const path = require('path');
const express = require('express');
const app = express();

const PORT = 3200;

app.use(express.static(__dirname + '/assets'));

app.get('/', (req, res) => {
    console.log(__dirname);
    res.sendFile(path.join(__dirname + '/') + 'index.html');
});

app.get('/approve/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/approve` + `/${params}`) + '.html');
});


app.get('/message/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/message` + `/${params}`) + '.html');
});

app.get('/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
});

app.listen(PORT, () => {
    console.log(`server is listening on port: http://localhost:${PORT}`);
});
