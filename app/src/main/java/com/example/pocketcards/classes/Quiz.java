package com.example.pocketcards.classes;

import java.util.List;

public class Quiz { //UNUSED CLASS

	private double timer;
	private int score;
	private int quizID;
	private String quizName;

	public void createQuiz(String quizName, List<Quiz> quizSet) {
		throw new UnsupportedOperationException();
	}

	public void createTeacherQuiz(String quizName) {
		throw new UnsupportedOperationException();
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}

	public int getScore() {
		return this.score;
	}

	public int getQuizID() {
		return this.quizID;
	}

	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	public String getQuizName() {
		return this.quizName;
	}

}