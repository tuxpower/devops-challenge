var restify = require('restify');

const server = restify.createServer({
  name: 'devops-test',
  version: '1.0.0'
});

var db = require('./queries');

server.use(restify.plugins.acceptParser(server.acceptable));
server.use(restify.plugins.queryParser());
server.use(restify.plugins.bodyParser());

server.get('/hello/:username', db.getDateOfBirthByUsername)
server.put('/hello/:username', db.createUser)

server.listen(8080, function () {
  console.log('%s listening at %s', server.name, server.url);
});
