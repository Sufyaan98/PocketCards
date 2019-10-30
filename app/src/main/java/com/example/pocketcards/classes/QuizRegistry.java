package com.example.pocketcards.classes;

import java.util.List;

public class QuizRegistry { //UNUSED CLASS

	private QuizRegistry instance;
	private List<Quiz> quizSet;
	private List<User> userGroup;

	public QuizRegistry() {
		throw new UnsupportedOperationException();
	}

	public QuizRegistry getInstance() {
		return this.instance;
	}

	public Quiz searchQuiz(String quizName) {
		throw new UnsupportedOperationException();
	}

}