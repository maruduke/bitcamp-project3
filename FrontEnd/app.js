const path = require('path');
const express = require('express');
const app = express();

const PORT = 3200;

app.use(express.static(__dirname + '/assets'));

app.get('/', (req, res) => {
    console.log(__dirname);
    res.sendFile(path.join(__dirname + '/') + 'index.html');
});

app.get('/mypage/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/mypage` + `/${params}`) + '.html');
});

app.get('/common/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/common` + `/${params}`) + '.html');
});

app.get('/approve/check/:params', (req, res) => {
    const { params } = req.params;
    res.sendFile(path.join(__dirname + `/approve/check` + `/${params}`) + '.html');
});

app.get('/approve/list/:params', (req, res) => {
    const { params } = req.params;
    res.sendFile(path.join(__dirname + `/approve/list` + `/${params}`) + '.html');
});

app.get('/approve/apply/:params', (req, res) => {
    const { params } = req.params;
    res.sendFile(path.join(__dirname + `/approve/apply` + `/${params}`) + '.html');
});

app.get('/approve/:params', (req, res) => {
    const { params } = req.params;
    res.sendFile(path.join(__dirname + `/approve` + `/${params}`) + '.html');
});

app.get('/message/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/message` + `/${params}`) + '.html');
});

app.get('/read', (req, res) => {
    const { documentId, type } = req.query;

    console.log(req.query);
    if (type == 'VACATION') res.redirect(`/approve/check/check_vac?documentId=${documentId}`);
    else if (type == 'BUSSINESSTRIP') res.redirect(`/approve/check/check_business?documentId=${documentId}`);
    else if (type == 'REPORT') res.redirect(`/approve/check/check_report?documentId=${documentId}`);
    else if (type == 'ACCOUNTINGEXPENSE') res.redirect(`/approve/check/check_expense?documentId=${documentId}`);
});

app.get('/:params', (req, res) => {
    const { params } = req.params;
    console.log(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
    res.sendFile(path.join(__dirname + `/${params}` + `/${params}`) + '.html');
});

app.listen(PORT, () => {
    console.log(`server is listening on port: http://localhost:${PORT}`);
});
