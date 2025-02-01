const express = require('express');
const quizController = require('./../controllers/quizController');

const router = express.Router();

router.route('/apiTest').get(quizController.apiTestFun);

// arabic quizzes filter
router
  .route('/arabicQuizzes')
  .get(quizController.aliasArabicQiuzzes, quizController.getAllQuizzes);

//english quizzes filter
router
  .route('/englishQuizzes')
  .get(quizController.aliasEnglishQiuzzes, quizController.getAllQuizzes);

//get all quizzes
router
  .route('/')
  .get(quizController.getAllQuizzes)
  .post(quizController.createQuiz);

//create mutli quizzes
router.route('/insertManyQuizzes').post(quizController.createManyQuizzes);

//delete quiz
router.route('/:id').delete(quizController.deleteQuiz);

router.route('/answer/:id').post(quizController.ansewrQuiz);

module.exports = router;
