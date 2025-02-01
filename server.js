const mongoose = require('mongoose');
const dotenv = require('dotenv');
// const { MongoClient, ServerApiVersion } = require('mongodb');
// const app = require('./app');

process.on('uncaughtException', err => {
  console.log('UNCAUGHT EXCEPTION! 💥 Shutting down...');
  console.log(err.name, err.message);
  process.exit(1);
});

dotenv.config({ path: './config.env' });
const app = require('./app');

const DB =
  'mongodb+srv://quizzAdmin:pnt05EJPLsNYwJgN@cluster0.y4ijd.mongodb.net/QuizzesDB?retryWrites=true&w=majority&appName=Cluster0';

mongoose.connect(DB).then(() => {
  console.log('MongoDB connection successful');

  const port = process.env.PORT || 3000;
  const server = app.listen(port, () => {
    console.log(`App running on port ${port}...`);
  });

  process.on('unhandledRejection', err => {
    console.log('UNHANDLED REJECTION! 💥 Shutting down...');
    console.log(err.name, err.message);
    server.close(() => {
      process.exit(1);
    });
  });
});
