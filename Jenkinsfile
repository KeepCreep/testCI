pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'set > env.txt' 
				for (String i : readFile('env.txt').split("\r?\n")) {
					 println i
				}
            }
        }
    }