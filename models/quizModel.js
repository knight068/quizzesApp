// const slugify = require('slugify');
const mongoose = require('mongoose');

const quizSchema = new mongoose.Schema({
  question: {
    type: String,
    required: true,
    unique: true
  },
  slug: String,
  difficulty: {
    type: String,
    enum: ['easy', 'medium', 'hard'],
    required: true
  },
  answers: [
    {
      text: {
        type: String,
        required: true
      },
      isCorrect: {
        type: Boolean,
        required: true
      }
    }
  ],
  category: {
    type: String,
    default: 'trivia',
    enum: ['trivia', 'sports', 'movie']
  },
  language: {
    type: String,
    default: 'english',
    enum: ['english', 'arabic']
  }
});

// quizSchema.pre('save', function(next) {
//   this.slug = slugify(this.name, { lower: true });
//   next();
// });

const quizzes = mongoose.model('quizzes', quizSchema);

module.exports = quizzes;
