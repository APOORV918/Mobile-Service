pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                bat 'mvn compile'
            }
        }
        
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Deploy'
            }
        }
    }
}