const Quiz = require('./../models/quizModel');
const catchAsync = require('../utils/catchAsync');
const AppError = require('../utils/appError');
// const APIFeatures = require('./../utils/apiFeatures');

exports.apiTestFun = (req, res, next) => {
  res.status(200).json({
    message: 'api is working'
  });
  next();
};

exports.aliasArabicQiuzzes = (req, res, next) => {
  req.query.language = 'arabic';
  next();
};

exports.aliasEnglishQiuzzes = (req, res, next) => {
  req.query.language = 'english';
  next();
};

exports.getAllQuizzes = catchAsync(async (req, res, next) => {
  const data = await Quiz.find(req.query);
  res.status(200).json({
    results: data.length,
    status: 'success',
    data: {
      data
    }
  });
  next();
});

exports.createQuiz = catchAsync(async (req, res, next) => {
  const doc = await Quiz.create(req.body);

  res.status(201).json({
    status: 'success',
    data: {
      doc
    },
    message: 'someString'
  });
  next();
});

exports.createManyQuizzes = catchAsync(async (req, res, next) => {
  const doc = await Quiz.insertMany(req.body);
  res.status(201).json({
    status: 'success',
    data: {
      doc
    }
  });
  next();
});
exports.deleteQuiz = catchAsync(async (req, res, next) => {
  const doc = await Quiz.findByIdAndDelete(req.params.id);

  if (!doc) {
    return next(new AppError('not document found with thet id', 404));
  }
  res.status(204).json({
    status: 'success',
    data: null
  });
});

exports.ansewrQuiz = catchAsync(async (req, res, next) => {
  const currentQuiz = await Quiz.findById(req.params.id);
  const userAnswer = req.body.answer - 1;
  if (!currentQuiz) {
    return next(new AppError('not document found with thet id', 404));
  }
  if (currentQuiz.answers[userAnswer].isCorrect) {
    res.status(200).json({
      status: 'succes',
      data: {
        // currentQuiz: currentQuiz,
        answer: 'right'
      }
    });
  } else {
    let correctAnswer = 0;
    for (let i = 0; i < currentQuiz.answers.length; ++i) {
      if (currentQuiz.answers[i].isCorrect) {
        correctAnswer = i;
      }
    }
    res.status(200).json({
      status: 'succes',
      data: {
        answer: 'wrong',
        correctAnswerIndex: correctAnswer
      }
    });
  }
  // console.log(currentQuiz);
  // console.log(userAnswer);
});
