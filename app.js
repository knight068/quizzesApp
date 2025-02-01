const express = require('express');
const mongoose = require('mongoose');
const dotenv = require('dotenv');
const path = require('path');
const morgan = require('morgan');
const helmet = require('helmet');
const cookieParser = require('cookie-parser');
const mongoSanitize = require('express-mongo-sanitize');
const xss = require('xss-clean');

const quizRoutes = require('./routes/quizRoutes');

const app = express();

// Serving static files
app.use(express.static(path.join(__dirname, 'public')));

// Middlewares
app.use(helmet());

// Development logging
if (process.env.NODE_ENV === 'development') {
  app.use(morgan('dev'));
}

app.use(express.json({ limit: '10kb' }));
app.use(express.urlencoded({ extended: true, limit: '10kb' }));
app.use(cookieParser());

// Data sanitization against NoSQL query injection
app.use(mongoSanitize());

// Data sanitization against XSS
app.use(xss());

app.get('/', (req, res) => {
  res.status(200).send('hello from server side');
});

// Routes
app.use('/api/v1/quizzes', quizRoutes);

// dotenv.config({ path: './config.env' });

// const DB = process.env.DATABASE;

// mongoose
//   .connect(DB, {
//     useNewUrlParser: true,
//     useCreateIndex: true,
//     useFindAndModify: false
//   })
//   .then(() => console.log('DB connection successful!'));

// const port = 3000;
// app.listen(port, () => {
//   console.log(`app is running on port ${port}...`);
// });

module.exports = app;
